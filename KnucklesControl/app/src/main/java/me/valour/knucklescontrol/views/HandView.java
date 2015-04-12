package me.valour.knucklescontrol.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import me.valour.knucklescontrol.R;

/**
 * Created by alice on 4/11/15.
 */
public class HandView extends View {

    public int tempChange;
    public float[] fingersTemp;

    private int mTextWidth;
    private int mTextHeight;
    private Paint mTextPaint;

    public HandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fingersTemp = new float[5];

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.HandView,
                0, 0);

        try {

            tempChange = a.getInteger(R.styleable.HandView_tempChange, 0);

            fingersTemp[0] = a.getFloat(R.styleable.HandView_temp0, 0);
            fingersTemp[1] = a.getFloat(R.styleable.HandView_temp1, 0);
            fingersTemp[2] = a.getFloat(R.styleable.HandView_temp2, 0);
            fingersTemp[3] = a.getFloat(R.styleable.HandView_temp3, 0);
            fingersTemp[4] = a.getFloat(R.styleable.HandView_temp4, 0);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        mTextWidth = 20;
        mTextHeight = 20;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom){
        measureView();

        double angleDelta = Math.PI*2.0/bowls.size();
        double topX = 0;
        double topY = -1.0*tableRadius;

        int i= 0;
        for(BowlView bowl: bowls){
            bowl.bringToFront();
            double angle = angleDelta*i;
            double px = Math.cos(angle)*topX - Math.sin(angle)*topY + centerX;
            double py = Math.sin(angle)*topX - Math.cos(angle)*topY + centerY;
            bowl.setAngle(angle);
            bowl.move((float)px, (float)py);
            i++;
        }

        super.onLayout(changed, left, top, right, bottom);
    }

    public void measureView(){
        if(measuredScreen){
            return;
        } else {
            mMeasuredWidth = getMeasuredWidth();
            mMeasuredHeight = getMeasuredHeight();
            int cx = mMeasuredWidth / 2;
            int cy = mMeasuredHeight / 2;
            tableRadius = Math.min(cx, cy);
            centerX = (float)cx;
            centerY = (float)cy;

            double q = ((double) tableRadius * 2.0 * Math.PI)
                    / (double) Kitchen.maxBowls;
            bowlRadius = (int) (q / 2.0);
            tableRadius -= bowlRadius;

            newBowl.setRadius(bowlRadius);
            newBowl.setTextSize(bowlRadius);
            newBowl.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            newBowl.setPadding(0, -1*bowlRadius/2, 0, 0);
            for(BowlView bv: bowls){
                bv.setRadius(bowlRadius);
            }
            measuredScreen = true;

            newBowl.setX(centerX);
            newBowl.setY(centerY-(bowlRadius/2));

            float s = (float)(2*tableRadius-4*bowlRadius);
            if((s*0.9)>2*bowlRadius){
                s *= 0.9;
            }
            trashBowl.setMinimumHeight((int)s);
            trashBowl.setMinimumWidth((int)s);
            trashBowl.setX(centerX-s/2);
            trashBowl.setY(centerY-s/2);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);
        int d = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(d, d);

    }

    private int measure(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            result = 500;
        } else {
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        Path p = new Path();
        p.moveTo(20, 20);
    }
}
