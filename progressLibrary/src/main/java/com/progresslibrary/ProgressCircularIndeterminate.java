package com.progresslibrary;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


//   https://github.com/zeng1990java/ProgressCircularIndeterminate
public class ProgressCircularIndeterminate extends View {

    private static final int DEFAULT_RADIUS = 15;

    private boolean isFirstAnimOver = false;
    private int mDrawCount = 0;
    private Paint mFirstPaint = new Paint();
    private Paint mSecondPaint = new Paint();
    private Paint mRingPaint = new Paint();

    private float mRadius1 = 0;
    private float mRadius2 = 0;
    private float mRingWidth = 4;
    private int mRingColor;
    private int mCircleColor;

    private int mSweepAngle = 1;
    private int mStartAngle = 0;
    private int mLimiteAngle = 0;

    private float mRotateAngle = 0.0F;

    private RectF mRectF = new RectF();

    public ProgressCircularIndeterminate(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttrs(context, attrs);

        setupPaints();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressCircularIndeterminate);

        mRingWidth = a.getDimensionPixelSize(R.styleable.ProgressCircularIndeterminate_ringWidth, getResources().getDimensionPixelSize(R.dimen.progress_ring_width));
        mRingColor = a.getColor(R.styleable.ProgressCircularIndeterminate_ringColor, getResources().getColor(R.color.progress_ring_color));
        mCircleColor = a.getColor(R.styleable.ProgressCircularIndeterminate_circleColor, getResources().getColor(R.color.progress_circle_color));

        a.recycle();
    }

    private void setupPaints() {
        mFirstPaint.setAntiAlias(true);
        mFirstPaint.setColor(mCircleColor);

        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setColor(mCircleColor);
        mSecondPaint.setStyle(Paint.Style.STROKE);
        mSecondPaint.setStrokeCap(Paint.Cap.SQUARE);

        mRingPaint.setAntiAlias(true);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStrokeWidth(mRingWidth);
        mRingPaint.setStrokeCap(Paint.Cap.SQUARE);
    }

    public void startProgress(){
        mRadius1 = 0;
        mRadius2 = 0;
        mDrawCount = 0;
        isFirstAnimOver = false;
        mStartAngle = 0;
        mSweepAngle = 1;
        mLimiteAngle = 0;
        mRotateAngle = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY){
            width = (int) (dp2px(DEFAULT_RADIUS)*2);
        }

        if (heightMode != MeasureSpec.EXACTLY){
            height = (int) (dp2px(DEFAULT_RADIUS)*2);
        }


        int w = Math.min(width, height);

        setMeasuredDimension(w, w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isFirstAnimOver){
            drawFirstAnim(canvas);
        }

        if (mDrawCount > 0){
            drawSecondAnim(canvas);
        }

        invalidate();
    }

    private void drawFirstAnim(Canvas canvas){
        if (mRadius1 < getWidth()/2){
            mRadius1 += 2;//加快速度
        }else {
            if (mRadius2 < getWidth()/2 - mRingWidth){
                mRadius2 += 2;//加快速度
            }else {
                mDrawCount++;
                if (mDrawCount >= 50){
                    if (mRadius2 < mRadius1){
                        mRadius2+=0.5f;
                    }
                }else {
                    mRadius2 = getWidth()/2 - mRingWidth;
                }
            }
        }
        if (mRadius1 > mRadius2) {
            if (mRadius2<=0){
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius1, mFirstPaint);
            }else {
                float rw = mRadius1 - mRadius2;
                mSecondPaint.setStrokeWidth(rw);
                mRectF.set(rw / 2, rw / 2, getWidth() - rw / 2, getHeight() - rw / 2);
                canvas.drawCircle(getWidth()/2, getHeight()/2, mRadius2 + rw/2, mSecondPaint);
            }

        }
    }

    private void drawSecondAnim(Canvas canvas){
        if (this.mStartAngle == this.mLimiteAngle) {
            this.mSweepAngle += 6;
        }
        if ((this.mSweepAngle >= 290) || (this.mStartAngle > this.mLimiteAngle))
        {
            this.mStartAngle += 6;
            this.mSweepAngle -= 6;
        }
        if (this.mStartAngle > this.mLimiteAngle + 290)
        {
            this.mLimiteAngle = this.mStartAngle;
            this.mStartAngle = this.mLimiteAngle;
            this.mSweepAngle = 1;
        }
        this.mRotateAngle += 4.0F;
        canvas.rotate(this.mRotateAngle, getWidth() / 2, getHeight() / 2);

        mRectF.set(mRingWidth / 2,mRingWidth / 2, getWidth() - mRingWidth / 2, getHeight() -mRingWidth/2);
        canvas.drawArc(mRectF, this.mStartAngle, this.mSweepAngle, false, mRingPaint);
    }

    private float dp2px(float dp){
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }


}
