/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.view.loadding;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.justwayward.reader.R;


public class LoadingView extends View {

    //the size in wrap_content model
    private static final int CIRCLE_DIAMETER = 56;

    private static final float CENTER_RADIUS = 15f;
    private static final float STROKE_WIDTH = 3.5f;

    private static final float MAX_PROGRESS_ARC = 300f;
    private static final float MIN_PROGRESS_ARC = 20f;

    private static final long ANIMATOR_DURATION = 1332;

    private Rect bounds;
    private Ring mRing;

    private Animator animator = null;
    private AnimatorSet animatorSet = null;
    private boolean mIsAnimatorCancel = false;

    private Interpolator interpolator = null;
    //the ring's RectF
    private final RectF mTempBounds = new RectF();
    //绘制半圆的paint
    private Paint mPaint;
    private final int DEFAULT_COLOR = 0xFF3B99DF;
    private boolean mAnimationStarted = false;
    //the ring style
    static final int RING_STYLE_SQUARE = 0;
    static final int RING_STYLE_ROUND = 1;

    //the animator style
    static final int PROGRESS_STYLE_MATERIAL = 0;
    static final int PROGRESS_STYLE_LINEAR = 1;

    private float mRotation = 0f;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRing = new Ring();
        bounds = new Rect();
        mPaint = new Paint();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRing.strokeWidth);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingView, 0, 0);
            setColor(a.getInt(R.styleable.LoadingView_loadding_color, DEFAULT_COLOR));
            setRingStyle(a.getInt(R.styleable.LoadingView_ring_style, RING_STYLE_SQUARE));
            setProgressStyle(a.getInt(R.styleable.LoadingView_progress_style, PROGRESS_STYLE_MATERIAL));
            setStrokeWidth(a.getDimension(R.styleable.LoadingView_ring_width, dp2px(STROKE_WIDTH)));
            setCenterRadius(a.getDimension(R.styleable.LoadingView_ring_radius, dp2px(CENTER_RADIUS)));
            a.recycle();
        }
    }

    /**
     * set the ring strokeWidth
     *
     * @param stroke
     */
    public void setStrokeWidth(float stroke) {
        mRing.strokeWidth = stroke;
        mPaint.setStrokeWidth(stroke);
    }

    public void setCenterRadius(float radius) {
        mRing.ringCenterRadius = radius;
    }

    public void setRingStyle(int style) {
        switch (style) {
            case RING_STYLE_SQUARE:
                mPaint.setStrokeCap(Paint.Cap.SQUARE);
                break;
            case RING_STYLE_ROUND:
                mPaint.setStrokeCap(Paint.Cap.ROUND);
                break;
        }
    }

    /**
     * set the animator's interpolator
     *
     * @param style
     */
    public void setProgressStyle(int style) {
        switch (style) {
            case PROGRESS_STYLE_MATERIAL:
                interpolator = new FastOutSlowInInterpolator();
                break;
            case PROGRESS_STYLE_LINEAR:
                interpolator = new LinearInterpolator();
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = (int) dp2px(CIRCLE_DIAMETER);
        int height = (int) dp2px(CIRCLE_DIAMETER);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, height);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        final Ring ring = mRing;
        ring.setInsets(w, h);
        bounds.set(0, 0, w, h);
    }

    private void buildAnimator() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(ANIMATOR_DURATION);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotation = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator = valueAnimator;
        animatorSet = buildFlexibleAnimation();
        animatorSet.addListener(animatorListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mIsAnimatorCancel) {
            final Rect bounds = getBounds();
            final int saveCount = canvas.save();
            canvas.rotate(mRotation * 360, bounds.exactCenterX(), bounds.exactCenterY());
            drawRing(canvas, bounds);
            canvas.restoreToCount(saveCount);
        } else {
            canvas.restore();
        }
    }

    /**
     * draw the ring
     *
     * @param canvas to draw the Ring
     * @param bounds the ring's rect
     */
    private void drawRing(Canvas canvas, Rect bounds) {
        final RectF arcBounds = mTempBounds;
        final Ring ring = mRing;
        arcBounds.set(bounds);
        arcBounds.inset(ring.strokeInset, ring.strokeInset);
        canvas.drawArc(arcBounds, ring.start, ring.sweep, false, mPaint);
    }

    public void start() {
        if (mAnimationStarted) {
            return;
        }

        if (animator == null || animatorSet == null) {
            mRing.reset();
            buildAnimator();
        }

        animator.start();
        animatorSet.start();
        mAnimationStarted = true;
        mIsAnimatorCancel = false;
    }


    public void stop() {
        mIsAnimatorCancel = true;
        if (animator != null) {
            animator.end();
            animator.cancel();
        }
        if (animatorSet != null) {

            animatorSet.end();
            animatorSet.cancel();
        }
        animator = null;
        animatorSet = null;

        mAnimationStarted = false;
        mRing.reset();
        mRotation = 0;
        invalidate();
    }

    public Rect getBounds() {
        return bounds;
    }

    public void setBounds(Rect bounds) {
        this.bounds = bounds;
    }

    /**
     * build FlexibleAnimation to control the progress
     *
     * @return Animatorset for control the progress
     */
    private AnimatorSet buildFlexibleAnimation() {
        final Ring ring = mRing;
        AnimatorSet set = new AnimatorSet();
        ValueAnimator increment = ValueAnimator.ofFloat(0, MAX_PROGRESS_ARC - MIN_PROGRESS_ARC).setDuration(ANIMATOR_DURATION / 2);
        increment.setInterpolator(new LinearInterpolator());
        increment.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float sweeping = ring.sweeping;
                final float value = (float) animation.getAnimatedValue();
                ring.sweep = sweeping + value;
                invalidate();
            }
        });
        increment.addListener(animatorListener);
        ValueAnimator reduce = ValueAnimator.ofFloat(0, MAX_PROGRESS_ARC - MIN_PROGRESS_ARC).setDuration(ANIMATOR_DURATION / 2);
        reduce.setInterpolator(interpolator);
        reduce.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float sweeping = ring.sweeping;
                float starting = ring.starting;
                float value = (float) animation.getAnimatedValue();
                ring.sweep = sweeping - value;
                ring.start = starting + value;
            }
        });
        set.play(reduce).after(increment);
        return set;
    }


    public void setColor(int color) {
        mRing.color = color;
        mPaint.setColor(color);
    }

    public int getColor() {
        return mRing.color;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            start();
        } else {
            stop();
        }
    }

    /**
     * turn dp to px
     *
     * @param dp value
     * @return result px value
     */
    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState state = new SavedState(parcelable);
        state.ring = mRing;
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(state);
        mRing = savedState.ring;
    }

    static class Ring implements Parcelable {


        public float strokeInset = 0f;
        public float strokeWidth = 0f;
        public float ringCenterRadius = 0f;
        public float start = 0f;
        public float end = 0f;
        public float sweep = 0f;
        public float sweeping = MIN_PROGRESS_ARC;

        public float starting = 0f;
        public float ending = 0f;
        public int color;


        public void restore() {
            starting = start;
            sweeping = sweep;
            ending = end;
        }

        public void reset() {
            end = 0f;
            start = 0f;
            sweeping = MIN_PROGRESS_ARC;
            sweep = 0f;
            starting = 0f;
        }

        public void setInsets(int width, int height) {
            final float minEdge = (float) Math.min(width, height);
            float insets;
            if (ringCenterRadius <= 0 || minEdge < 0) {
                insets = (float) Math.ceil(strokeWidth / 2.0f);
            } else {
                insets = (minEdge / 2.0f - ringCenterRadius);
            }


            strokeInset = insets;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(this.strokeInset);
            dest.writeFloat(this.strokeWidth);
            dest.writeFloat(this.ringCenterRadius);
            dest.writeFloat(this.start);
            dest.writeFloat(this.end);
            dest.writeFloat(this.sweep);
            dest.writeFloat(this.sweeping);
            dest.writeFloat(this.starting);
            dest.writeFloat(this.ending);
            dest.writeInt(this.color);
        }

        public Ring() {
        }

        protected Ring(Parcel in) {
            this.strokeInset = in.readFloat();
            this.strokeWidth = in.readFloat();
            this.ringCenterRadius = in.readFloat();
            this.start = in.readFloat();
            this.end = in.readFloat();
            this.sweep = in.readFloat();
            this.sweeping = in.readFloat();
            this.starting = in.readFloat();
            this.ending = in.readFloat();
            this.color = in.readInt();
        }

        public static final Parcelable.Creator<Ring> CREATOR = new Parcelable.Creator<Ring>() {
            @Override
            public Ring createFromParcel(Parcel source) {
                return new Ring(source);
            }

            @Override
            public Ring[] newArray(int size) {
                return new Ring[size];
            }
        };
    }

    /**
     *
     */
    static class SavedState extends BaseSavedState {
        public Ring ring;


        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            ring = in.readParcelable(Ring.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.ring, flags);
        }


        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    /**
     * Listen the animatorSet and the IncrementAnimator;
     */
    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mIsAnimatorCancel) return;
            if (animation instanceof ValueAnimator) {
                mRing.sweeping = mRing.sweep;
            } else if (animation instanceof AnimatorSet) {
                mRing.restore();
                animatorSet.start();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
}