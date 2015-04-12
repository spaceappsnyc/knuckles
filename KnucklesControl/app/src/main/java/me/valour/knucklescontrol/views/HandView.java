package me.valour.knucklescontrol.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import me.valour.knucklescontrol.R;

/**
 * Created by alice on 4/11/15.
 */
public class HandView extends View {

    public int tempChange;
    public float[] fingersTemp;

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
    }
}
