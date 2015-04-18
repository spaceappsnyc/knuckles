        package me.valour.knucklescontrol.views;

        import android.content.Context;
        import android.content.res.TypedArray;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.LinearGradient;
        import android.graphics.Paint;
        import android.graphics.Path;
        import android.graphics.RadialGradient;
        import android.graphics.Shader;
        import android.util.AttributeSet;
        import android.view.View;

        import me.valour.knucklescontrol.R;

/**
 * Created by alice on 4/11/15.
 */
public class HandView extends View {

    public int tempChange;
    public float[] fingersTemp;
    public Position[] fingersPosition;
    public Position[] lightPosition;
    public double side;

    public boolean lights;

    private Paint textPaint;
    private Paint headingPaint;
    private Paint lightsPaint;

    private Paint lightsOnPaint;

    public HandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fingersTemp = new float[5];

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.HandView,
                0, 0);

        try {

            tempChange = a.getInteger(R.styleable.HandView_tempChange, 0);

            fingersTemp[0] = a.getFloat(R.styleable.HandView_temp0, 10);
            fingersTemp[1] = a.getFloat(R.styleable.HandView_temp1, 15);
            fingersTemp[2] = a.getFloat(R.styleable.HandView_temp2, 12);
            fingersTemp[3] = a.getFloat(R.styleable.HandView_temp3, 11);
            fingersTemp[4] = a.getFloat(R.styleable.HandView_temp4, 20);
        } finally {
            a.recycle();
        }

        lights = false;
        lightPosition = new Position[3];
        lightPosition[0] = new Position(30, 50);
        lightPosition[1] = new Position(40, 40);
        lightPosition[2] = new Position(48, 35);

        fingersPosition = new Position[5];
        fingersPosition[0] = new Position(80,32);
        fingersPosition[1] = new Position(43,5);
        fingersPosition[2] = new Position(32, 5);
        fingersPosition[3] = new Position(23, 15);
        fingersPosition[4] = new Position(15, 28);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(25);
        textPaint.setShadowLayer(3,2,2, Color.BLACK);

        headingPaint = new Paint();
        headingPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        headingPaint.setColor(Color.WHITE);
        headingPaint.setTextSize(50);
        headingPaint.setFakeBoldText(true);

        lightsPaint = new Paint();
        lightsPaint.setColor(Color.WHITE);
        lightsPaint.setStrokeWidth(3);
        lightsPaint.setStyle(Paint.Style.STROKE);
        lightsPaint.setAntiAlias(true);

        lightsOnPaint = new Paint();
        lightsOnPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);
        int d = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(d, d);
        side = (double)d;
    }

    private float getPix(double percent){
        double p = percent/100.0;
        return (float) Math.round(p*side);
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

    private Paint getHandPaint(){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(150);
        switch (tempChange){
            case -1:
                Shader shader = new LinearGradient(0, 0, 0, getPix(100.0), Color.DKGRAY, Color.BLUE, Shader.TileMode.CLAMP);
                paint.setShader(shader);
                break;
            case 1:
                Shader shader2 = new LinearGradient(0, 0, 0, getPix(100.0), Color.DKGRAY, Color.RED, Shader.TileMode.CLAMP);
                paint.setShader(shader2);
                break;
            default:
                paint.setColor(Color.DKGRAY);
                break;
        }

        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = getHandPaint();
        Path p = new Path();
        p.moveTo(getPix(35.09584), getPix(2.12858));
        p.quadTo(getPix(36.07), getPix(2.38), getPix(36.7), getPix(2.98));
        p.quadTo(getPix(37.42171), getPix(3.67189), getPix(38.67412), getPix(6.32982));
        p.quadTo(getPix(39.92651), getPix(9.07349), getPix(42.07348), getPix(15.84693));
        p.quadTo(getPix(44.22), getPix(22.62036), getPix(45.38), getPix(27.85048));
        p.cubicTo(getPix(45.3834), getPix(27.85048), getPix(46.57952), getPix(32.93), getPix(46.63), getPix(33.0806));
        p.cubicTo(getPix(46.69205), getPix(33.2306), getPix(46.8262), getPix(33.2949), getPix(47.0722), getPix(33.25207));
        p.cubicTo(getPix(47.31823000000001), getPix(33.2092), getPix(47.26198), getPix(32.82338), getPix(47.26198), getPix(32.82338));
        p.lineTo(getPix(46.45688), getPix(27.59326));
        p.quadTo(getPix(45.651759999999996), getPix(22.36314), getPix(43.952079999999995), getPix(16.10415));
        p.quadTo(getPix(42.341849999999994), getPix(9.84515), getPix(41.984019999999994), getPix(7.615920000000001));
        p.quadTo(getPix(41.626189999999994), getPix(5.3866900000000015), getPix(41.984019999999994), getPix(4.529300000000001));
        p.quadTo(getPix(42.34183999999999), getPix(3.671900000000001), getPix(43.41533), getPix(2.985980000000001));
        p.quadTo(getPix(44.48881), getPix(2.385810000000001), getPix(45.83067), getPix(2.728770000000001));
        p.quadTo(getPix(47.172509999999996), getPix(3.071730000000001), getPix(48.06709), getPix(3.9291300000000007));
        p.quadTo(getPix(49.05111), getPix(4.87227), getPix(50.12459), getPix(7.101500000000001));
        p.quadTo(getPix(51.19808), getPix(9.33074), getPix(52.53993), getPix(13.61772));
        p.quadTo(getPix(53.88178), getPix(17.9047), getPix(55.22363), getPix(23.906489999999998));
        p.quadTo(getPix(56.65494), getPix(29.994), getPix(56.92332), getPix(32.82342));
        p.quadTo(getPix(57.28115), getPix(35.65283), getPix(58.801899999999996), getPix(38.99667));
        p.quadTo(getPix(60.322669999999995), getPix(42.34051), getPix(61.9329), getPix(44.56975));
        p.quadTo(getPix(63.632569999999994), getPix(46.88472), getPix(65.06389), getPix(48.25656));
        p.quadTo(getPix(66.58465), getPix(49.6284), getPix(67.21085), getPix(49.54266));
        p.quadTo(getPix(67.83704999999999), getPix(49.45691), getPix(68.55270999999999), getPix(48.599509999999995));
        p.quadTo(getPix(69.35781999999999), getPix(47.827859999999994), getPix(71.77315999999999), getPix(42.597739999999995));
        p.quadTo(getPix(74.18848999999999), getPix(37.36762999999999), getPix(75.61979999999998), getPix(35.73855999999999));
        p.quadTo(getPix(77.05109999999998), getPix(34.10950999999999), getPix(78.57188999999998), getPix(32.99488999999999));
        p.quadTo(getPix(80.09263999999999), getPix(31.880269999999992), getPix(81.61341999999998), getPix(31.28009999999999));
        p.quadTo(getPix(83.22363999999997), getPix(30.67992999999999), getPix(84.74438999999998), getPix(30.67992999999999));
        p.quadTo(getPix(86.26515999999998), getPix(30.67992999999999), getPix(86.98081999999998), getPix(31.194369999999992));
        p.quadTo(getPix(87.69647999999998), getPix(31.794549999999994), getPix(87.60701999999998), getPix(32.39471999999999));
        p.quadTo(getPix(87.60701999999998), getPix(32.99488999999999), getPix(86.35462999999997), getPix(35.13837999999999));
        p.quadTo(getPix(85.10222999999998), getPix(37.28188999999999), getPix(82.95525999999997), getPix(42.94068999999999));
        p.quadTo(getPix(80.80828999999997), getPix(48.68524999999999), getPix(80.53991999999997), getPix(49.97132999999999));
        p.quadTo(getPix(80.36099999999996), getPix(51.343169999999986), getPix(80.53991999999997), getPix(52.200569999999985));
        p.quadTo(getPix(80.71883999999997), getPix(53.057949999999984), getPix(80.62936999999997), getPix(55.71589999999998));
        p.quadTo(getPix(80.53991999999997), getPix(58.45957999999998), getPix(80.18206999999997), getPix(59.402709999999985));
        p.quadTo(getPix(79.91372999999997), getPix(60.34583999999998), getPix(75.88815999999997), getPix(66.77630999999998));
        p.quadTo(getPix(71.95205999999997), getPix(73.29254999999998), getPix(69.80510999999997), getPix(75.69324999999998));
        p.quadTo(getPix(67.65813999999997), getPix(78.09395999999998), getPix(67.74759999999998), getPix(80.32318999999998));
        p.quadTo(getPix(67.83704999999998), getPix(82.63816999999999), getPix(69.35781999999998), getPix(90.52621999999998));
        p.lineTo(getPix(70.87857999999997), getPix(98.50000999999997));
        p.lineTo(getPix(56.83384999999997), getPix(98.50000999999997));
        p.lineTo(getPix(42.87857999999997), getPix(98.50000999999997));
        p.lineTo(getPix(42.96802999999997), getPix(95.92780999999998));
        p.quadTo(getPix(43.05748999999997), getPix(93.44136999999998), getPix(42.52074999999997), getPix(90.61196999999999));
        p.quadTo(getPix(42.07346999999997), getPix(87.78255999999999), getPix(40.28432999999997), getPix(84.95312999999999));
        p.quadTo(getPix(38.58464999999997), getPix(82.12371999999999), getPix(35.632569999999966), getPix(78.35117999999999));
        p.quadTo(getPix(32.68049999999997), getPix(74.66437999999998), getPix(30.891359999999967), getPix(71.66349999999998));
        p.quadTo(getPix(29.191689999999966), getPix(68.74833999999998), getPix(27.223629999999968), getPix(63.86117999999998));
        p.quadTo(getPix(25.34503999999997), getPix(59.059769999999986), getPix(24.27155999999997), getPix(54.94426999999998));
        p.quadTo(getPix(23.19807999999997), getPix(50.91448999999998), getPix(18.81468999999997), getPix(42.34052999999998));
        p.quadTo(getPix(14.431309999999971), getPix(33.85230999999998), getPix(13.715649999999972), getPix(31.79455999999998));
        p.quadTo(getPix(12.999989999999972), getPix(29.73679999999998), getPix(12.999989999999972), getPix(28.70792999999998));
        p.quadTo(getPix(12.999989999999972), getPix(27.76477999999998), getPix(13.894549999999972), getPix(26.82164999999998));
        p.quadTo(getPix(14.789129999999972), getPix(25.964249999999982), getPix(15.415319999999973), getPix(25.87850999999998));
        p.quadTo(getPix(16.041519999999974), getPix(25.79276999999998), getPix(16.667719999999974), getPix(26.04998999999998));
        p.quadTo(getPix(17.383359999999975), getPix(26.30720999999998), getPix(23.287529999999975), getPix(35.73857999999998));
        p.cubicTo(getPix(23.287529999999975), getPix(35.73857999999998), getPix(28.911769999999976), getPix(44.805549999999975), getPix(29.191679999999977), getPix(45.16994999999998));
        p.cubicTo(getPix(29.471579999999978), getPix(45.53433999999998), getPix(31.752019999999977), getPix(46.60609999999998), getPix(31.070269999999976), getPix(43.283679999999976));
        p.quadTo(getPix(30.388519999999975), getPix(39.961259999999974), getPix(31.070269999999976), getPix(43.283679999999976));
        p.quadTo(getPix(31.070269999999976), getPix(41.826099999999975), getPix(25.345039999999976), getPix(28.364979999999974));
        p.cubicTo(getPix(25.345039999999976), getPix(28.364979999999974), getPix(20.403259999999975), getPix(17.561779999999974), getPix(20.045429999999975), getPix(16.061339999999973));
        p.cubicTo(getPix(19.687599999999975), getPix(14.560899999999974), getPix(19.798709999999975), getPix(13.874969999999973), getPix(19.798709999999975), getPix(13.874969999999973));
        p.quadTo(getPix(19.977619999999973), getPix(12.846099999999973), getPix(20.603809999999974), getPix(12.245919999999973));
        p.quadTo(getPix(21.230019999999975), getPix(11.731489999999972), getPix(22.303489999999975), getPix(11.388519999999973));
        p.quadTo(getPix(23.466429999999974), getPix(11.131309999999973), getPix(24.092629999999975), getPix(11.302789999999973));
        p.quadTo(getPix(24.808279999999975), getPix(11.560009999999973), getPix(25.345039999999976), getPix(12.074449999999974));
        p.quadTo(getPix(25.881769999999975), getPix(12.674629999999974), getPix(26.597429999999974), getPix(14.475159999999974));
        p.quadTo(getPix(27.402539999999973), getPix(16.361429999999974), getPix(31.338649999999973), getPix(23.820779999999974));
        p.quadTo(getPix(35.274749999999976), getPix(31.280129999999975), getPix(36.25877999999997), getPix(34.109539999999974));
        p.cubicTo(getPix(36.25877999999997), getPix(34.109539999999974), getPix(37.07471999999997), getPix(36.55311999999997), getPix(37.33225999999998), getPix(36.93894999999998));
        p.cubicTo(getPix(37.589799999999975), getPix(37.324779999999976), getPix(38.75201999999997), getPix(38.26792999999998), getPix(38.49518999999998), getPix(36.253029999999974));
        p.quadTo(getPix(38.23834999999998), getPix(34.23815999999997), getPix(38.49518999999998), getPix(36.253029999999974));
        p.quadTo(getPix(38.49518999999998), getPix(35.30989999999997), getPix(36.88496999999998), getPix(29.050899999999974));
        p.quadTo(getPix(35.36419999999998), getPix(22.791909999999973), getPix(32.85940999999998), getPix(14.389419999999975));
        p.cubicTo(getPix(32.85940999999998), getPix(14.389419999999975), getPix(31.138089999999984), getPix(8.859209999999974), getPix(30.780259999999984), getPix(7.273029999999975));
        p.cubicTo(getPix(30.422429999999984), getPix(5.686849999999975), getPix(30.601349999999986), getPix(4.5722299999999745), getPix(30.601349999999986), getPix(4.5722299999999745));
        p.cubicTo(getPix(31.316999999999986), getPix(3.0289199999999745), getPix(31.494469999999986), getPix(2.7716999999999743), getPix(32.948869999999985), getPix(2.3858699999999744));
        p.cubicTo(getPix(34.40325999999999), getPix(2.0000399999999745), getPix(35.09583999999999), getPix(2.1286499999999746), getPix(35.09583999999999), getPix(2.1286499999999746));
        p.lineTo(getPix(35.095829999999985), getPix(2.1286299999999745));
        canvas.drawPath(p, paint);

        float totalTemp = 0;
        for(int i=0; i<5; i++){
            float x = getPix(fingersPosition[i].x);
            float y = getPix(fingersPosition[i].y);
            canvas.save();
            canvas.rotate(65, x, y);
            canvas.drawText(String.format("%.1f C", fingersTemp[i]),
                    x, y, textPaint);
            canvas.restore();
            totalTemp += fingersTemp[i];
        }
        float avgTemp = totalTemp / 5;
        canvas.drawText("Avg Temp",getPix(35.0), getPix(60.0), headingPaint);
        canvas.drawText(String.format("%.1f C", avgTemp), getPix(40.0), getPix(70.0), headingPaint);

        int radius = 50;
        for(int i=0; i<lightPosition.length; i++){
            Position pos = lightPosition[i];
            float x = getPix(pos.x);
            float y = getPix(pos.y);

            Paint lightColor;
            if(lights) {
                Shader radialShader = new RadialGradient(x, y, radius, Color.WHITE, Color.TRANSPARENT, Shader.TileMode.CLAMP);
                lightsOnPaint.setShader(radialShader);
                canvas.drawCircle(x, y, radius, lightsOnPaint);
            }
            canvas.drawCircle(x, y, radius/2, lightsPaint);
        }

       // drawGrid(canvas);
    }

    public void drawGrid(Canvas canvas){
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.STROKE);
        for(int x=10; x<100; x+=10){
            float px = getPix(x);
            canvas.drawLine(px, 0, px, (float)side, p);
        }
        for(int y=10; y<100; y+=10){
            float py = getPix(y);
            canvas.drawLine(0, py, (float)side, py, p);
        }
    }

    public class Position{
        public final float x;
        public final float y;

        public Position(float x, float y){
            this.x = x;
            this.y = y;
        }
    }
}