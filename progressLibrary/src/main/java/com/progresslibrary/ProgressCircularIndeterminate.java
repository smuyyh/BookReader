package com.progresslibrary;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

//一大一小效果
public class ProgressCircularIndeterminate extends View {

    private static final int DEFAULT_RADIUS = 15;

    private Paint leftPaint = new Paint();
    private Paint rightPaint = new Paint();

    private float mRadius = 0;
    private int leftColor;
    private int rightColor;
    private boolean isRight = false;//用于标记x1点向右边行驶
    private boolean isFirst = true;

    private Handler handler = new Handler(){//作为延迟处理
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };
    public ProgressCircularIndeterminate(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressCircularIndeterminate);

        leftColor = a.getColor(R.styleable.ProgressCircularIndeterminate_leftColor, getResources().getColor(R.color.progress_left_color));
        rightColor = a.getColor(R.styleable.ProgressCircularIndeterminate_rightColor, getResources().getColor(R.color.progress_right_color));

        a.recycle();
        setupPaints();
    }

    private void setupPaints() {
        leftPaint.setAntiAlias(true);
        leftPaint.setColor(leftColor);
        leftPaint.setDither(true);

        rightPaint.setAntiAlias(true);
        rightPaint.setColor(rightColor);
        rightPaint.setDither(true);
    }

    public void startProgress(){
        isRight = false;
        mRadius = getHeight()/4;
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


        int h = Math.min(width, height);

        setMeasuredDimension(h*2, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isFirst) {
            isFirst = false;
            startProgress();
        }
        drawFirstAnim(canvas);

    }

    private void drawFirstAnim(Canvas canvas){
        int r = getHeight()/2;
        if (isRight) {
            mRadius ++;
            if (mRadius == r)
                isRight = false;
        } else {
            mRadius--;
            if (mRadius == 0)
                isRight = true;
        }


        float x1 = r + mRadius;
        float y1 = r;
        float x2 = getHeight() + mRadius;//2*r + mRadius
        float y2 = r;

        canvas.drawCircle(x1, y1, r - mRadius, leftPaint);
        canvas.drawCircle(x2, y2, mRadius, rightPaint);

        handler.sendEmptyMessageDelayed(0,21);
    }



    private float dp2px(float dp){
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }


}
