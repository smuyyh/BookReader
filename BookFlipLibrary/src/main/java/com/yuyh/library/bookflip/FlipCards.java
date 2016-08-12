package com.yuyh.library.bookflip;

import android.view.MotionEvent;
import android.view.View;

import com.yuyh.library.utils.AphidLog;
import com.yuyh.library.utils.TextureUtils;
import com.yuyh.library.utils.UI;

import junit.framework.Assert;

import javax.microedition.khronos.opengles.GL10;

public class FlipCards {

    private static final float ACCELERATION = 0.65f;
    private static final float MOVEMENT_RATE = 1.5f;
    private static final int MAX_TIP_ANGLE = 60;
    private static final int MAX_TOUCH_MOVE_ANGLE = 15;
    private static final float MIN_MOVEMENT = 4f;

    private static final int STATE_INIT = 0;
    private static final int STATE_TOUCH = 1;
    private static final int STATE_AUTO_ROTATE = 2;

    public ViewDualCards frontCards;
    public ViewDualCards backCards;

    private float accumulatedAngle = 0f;
    private boolean forward = true;
    private int animatedFrame = 0;
    private int state = STATE_INIT;

    private boolean orientationVertical = true;
    private float lastPosition = -1;

    private FlipViewController controller;

    private volatile boolean visible = false;

    private volatile boolean firstDrawFinished = false;

    private int maxIndex = 0;

    private int lastPageIndex;

    public FlipCards(FlipViewController controller, boolean orientationVertical) {
        this.controller = controller;

        frontCards = new ViewDualCards(orientationVertical);
        backCards = new ViewDualCards(orientationVertical);
        this.orientationVertical = orientationVertical;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isFirstDrawFinished() {
        return firstDrawFinished;
    }

    public void setFirstDrawFinished(boolean firstDrawFinished) {
        this.firstDrawFinished = firstDrawFinished;
    }

    boolean refreshPageView(View view) {
        boolean match = false;
        if (frontCards.getView() == view) {
            frontCards.resetWithIndex(frontCards.getIndex());
            match = true;
        }
        if (backCards.getView() == view) {
            backCards.resetWithIndex(backCards.getIndex());
            match = true;
        }

        return match;
    }

    boolean refreshPage(int pageIndex) {
        boolean match = false;
        if (frontCards.getIndex() == pageIndex) {
            frontCards.resetWithIndex(pageIndex);
            match = true;
        }
        if (backCards.getIndex() == pageIndex) {
            backCards.resetWithIndex(pageIndex);
            match = true;
        }

        return match;
    }

    void refreshAllPages() {
        frontCards.resetWithIndex(frontCards.getIndex());
        backCards.resetWithIndex(backCards.getIndex());
    }

    public void reloadTexture(int frontIndex, View frontView, int backIndex, View backView) {
        synchronized (this) {
            boolean
                    frontChanged =
                    frontCards.loadView(frontIndex, frontView, controller.getAnimationBitmapFormat());
            boolean
                    backChanged =
                    backCards.loadView(backIndex, backView, controller.getAnimationBitmapFormat());

            if (AphidLog.ENABLE_DEBUG) {
                AphidLog
                        .d("reloading texture: %s and %s; old views: %s, %s, front changed %s, back changed %s",
                                frontView, backView, frontCards.getView(), backCards.getView(), frontChanged,
                                backChanged);
            }

            if (AphidLog.ENABLE_DEBUG) {
                AphidLog.d("reloadTexture: activeIndex %d, front %d, back %d, angle %.1f",
                        getPageIndexFromAngle(accumulatedAngle), frontIndex, backIndex,
                        accumulatedAngle);
            }
        }
    }

    synchronized void resetSelection(int selection, int maxIndex) {
        UI.assertInMainThread();

        //stop flip animation when selection is manually changed

        this.maxIndex = maxIndex;
        setVisible(false);
        setState(STATE_INIT);
        accumulatedAngle = selection * 180;
        frontCards.resetWithIndex(selection);
        backCards.resetWithIndex(selection + 1 < maxIndex ? selection + 1 : -1);
        controller.postHideFlipAnimation();
    }

    public synchronized void draw(FlipRenderer renderer, GL10 gl) {
        frontCards.buildTexture(renderer, gl);
        backCards.buildTexture(renderer, gl);

        if (!TextureUtils.isValidTexture(frontCards.getTexture()) &&
                !TextureUtils.isValidTexture(backCards.getTexture())) {
            return;
        }

        if (!visible) {
            return;
        }

        switch (state) {
            case STATE_INIT:
            case STATE_TOUCH:
                break;
            case STATE_AUTO_ROTATE: {
                animatedFrame++;
                float delta = (forward ? ACCELERATION : -ACCELERATION) * animatedFrame % 180;

                float oldAngle = accumulatedAngle;

                accumulatedAngle += delta;

                if (oldAngle < 0) { //bouncing back after flip backward and over the first page
                    Assert.assertTrue(forward);
                    if (accumulatedAngle >= 0) {
                        accumulatedAngle = 0;
                        setState(STATE_INIT);
                    }
                } else {
                    if (frontCards.getIndex() == maxIndex - 1 && oldAngle > frontCards.getIndex()
                            * 180) { //bouncing back after flip forward and over the last page
                        Assert.assertTrue(!forward);
                        if (accumulatedAngle <= frontCards.getIndex() * 180) {
                            setState(STATE_INIT);
                            accumulatedAngle = frontCards.getIndex() * 180;
                        }
                    } else {
                        if (forward) {
                            Assert.assertTrue(
                                    "index of backCards should not be -1 when automatically flipping forward",
                                    backCards.getIndex() != -1);
                            if (accumulatedAngle >= backCards.getIndex() * 180) { //moved to the next page
                                accumulatedAngle = backCards.getIndex() * 180;
                                setState(STATE_INIT);
                                controller.postFlippedToView(backCards.getIndex());

                                swapCards();
                                backCards.resetWithIndex(frontCards.getIndex() + 1);
                            }
                        } else { //backward
                            if (accumulatedAngle <= frontCards.getIndex() * 180) { //firstCards restored
                                accumulatedAngle = frontCards.getIndex() * 180;
                                setState(STATE_INIT);
                            }
                        }
                    }
                } //ends of `if (oldAngle < 0) {} else {}`

                if (state == STATE_INIT) {
                    controller.postHideFlipAnimation();
                } else {
                    controller.getSurfaceView().requestRender();
                }
            }
            break;
            default:
                AphidLog.e("Invalid state: " + state);
                break;
        }

        float angle = getDisplayAngle();
        if (angle < 0) {
            frontCards.getTopCard().setAxis(Card.AXIS_BOTTOM);
            frontCards.getTopCard().setAngle(-angle);
            frontCards.getTopCard().draw(gl);

            frontCards.getBottomCard().setAngle(0);
            frontCards.getBottomCard().draw(gl);

            //no need to draw backCards here
        } else {
            if (angle < 90) { //render front view over back view
                frontCards.getTopCard().setAngle(0);
                frontCards.getTopCard().draw(gl);

                backCards.getBottomCard().setAngle(0);
                backCards.getBottomCard().draw(gl);

                frontCards.getBottomCard().setAxis(Card.AXIS_TOP);
                frontCards.getBottomCard().setAngle(angle);
                frontCards.getBottomCard().draw(gl);
            } else { //render back view first
                frontCards.getTopCard().setAngle(0);
                frontCards.getTopCard().draw(gl);

                backCards.getTopCard().setAxis(Card.AXIS_BOTTOM);
                backCards.getTopCard().setAngle(180 - angle);
                backCards.getTopCard().draw(gl);

                backCards.getBottomCard().setAngle(0);
                backCards.getBottomCard().draw(gl);
            }
        }

        if ((frontCards.getView() == null || TextureUtils.isValidTexture(frontCards.getTexture())) &&
                (backCards.getView() == null || TextureUtils.isValidTexture(backCards.getTexture()))
                )
            firstDrawFinished = true;
    }

    public void invalidateTexture() {
        frontCards.abandonTexture();
        backCards.abandonTexture();
    }

    public synchronized boolean handleTouchEvent(MotionEvent event, boolean isOnTouchEvent) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // remember page we started on...
                lastPageIndex = getPageIndexFromAngle(accumulatedAngle);
                lastPosition = orientationVertical ? event.getY() : event.getX();
                return isOnTouchEvent;
            case MotionEvent.ACTION_MOVE:
                float delta = orientationVertical ? (lastPosition - event.getY()) : (lastPosition - event.getX());

                if (Math.abs(delta) > controller.getTouchSlop()) {
                    setState(STATE_TOUCH);
                    forward = delta > 0;
                }
                if (state == STATE_TOUCH) {
                    if (Math.abs(delta) > MIN_MOVEMENT) { //ignore small movements
                        forward = delta > 0;
                    }

                    controller.showFlipAnimation();

                    float angleDelta;
                    if (orientationVertical) {
                        angleDelta = 180 * delta / controller.getContentHeight() * MOVEMENT_RATE;
                    } else {
                        angleDelta = 180 * delta / controller.getContentWidth() * MOVEMENT_RATE;
                    }

                    if (Math.abs(angleDelta) > MAX_TOUCH_MOVE_ANGLE) { //prevent large delta when moving too fast
                        angleDelta = Math.signum(angleDelta) * MAX_TOUCH_MOVE_ANGLE;
                    }

                    // do not flip more than one page with one touch...
                    if (Math.abs(getPageIndexFromAngle(accumulatedAngle + angleDelta) - lastPageIndex) <= 1) {
                        accumulatedAngle += angleDelta;
                    }

                    //Bounce the page for the first and the last page
                    if (frontCards.getIndex() == maxIndex - 1) { //the last page
                        if (accumulatedAngle > frontCards.getIndex() * 180) {
                            accumulatedAngle =
                                    Math.min(accumulatedAngle,
                                            controller.isOverFlipEnabled() ? (frontCards.getIndex() * 180
                                                    + MAX_TIP_ANGLE)
                                                    : (frontCards.getIndex() * 180));
                        }
                    }

                    if (accumulatedAngle < 0) {
                        accumulatedAngle = Math.max(accumulatedAngle, controller.isOverFlipEnabled() ? -MAX_TIP_ANGLE : 0);
                    }

                    int anglePageIndex = getPageIndexFromAngle(accumulatedAngle);

                    if (accumulatedAngle >= 0) {
                        if (anglePageIndex != frontCards.getIndex()) {
                            if (anglePageIndex == frontCards.getIndex() - 1) { //moved to previous page
                                swapCards(); //frontCards becomes the backCards
                                frontCards.resetWithIndex(backCards.getIndex() - 1);
                                controller.flippedToView(anglePageIndex, false);
                            } else if (anglePageIndex == frontCards.getIndex() + 1) { //moved to next page
                                swapCards();
                                backCards.resetWithIndex(frontCards.getIndex() + 1);
                                controller.flippedToView(anglePageIndex, false);
                            } else {
                                throw new RuntimeException(AphidLog.format(
                                        "Inconsistent states: anglePageIndex: %d, accumulatedAngle %.1f, frontCards %d, backCards %d",
                                        anglePageIndex, accumulatedAngle, frontCards.getIndex(), backCards.getIndex()));
                            }
                        }
                    }

                    lastPosition = orientationVertical ? event.getY() : event.getX();

                    controller.getSurfaceView().requestRender();
                    return true;
                }

                return isOnTouchEvent;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (state == STATE_TOUCH) {
                    if (accumulatedAngle < 0) {
                        forward = true;
                    } else if (accumulatedAngle >= frontCards.getIndex() * 180
                            && frontCards.getIndex() == maxIndex - 1) {
                        forward = false;
                    }

                    setState(STATE_AUTO_ROTATE);
                    controller.getSurfaceView().requestRender();
                }
                return isOnTouchEvent;
        }

        return false;
    }

    public void swapCards() {
        ViewDualCards tmp = frontCards;
        frontCards = backCards;
        backCards = tmp;
    }

    public void setState(int state) {
        if (this.state != state) {                        /*
            if (AphidLog.ENABLE_DEBUG)
				AphidLog.i("setState: from %d, to %d; angle %.1f", this.state, state, angle);
			*/
            this.state = state;
            animatedFrame = 0;
        }
    }

    private int getPageIndexFromAngle(float angle) {
        return ((int) angle) / 180;
    }

    private float getDisplayAngle() {
        return accumulatedAngle % 180;
    }
}