package com.example.ravinder.arttherapy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravinder on 2/14/15.
 */
public class PaintView extends View{

    private static final String TAG = "PaintView";

    private List<Point> points = new ArrayList<Point>();
    private Paint paint = new Paint();



    public PaintView(Context context) {
        super(context);
        paint.setColor(Color.RED);
    }

    public PaintView(Context context, AttributeSet attrs) {
        this(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point point = new Point();
        point.x = event.getX();
        point.y = event.getY();
        points.add(point);

        invalidate();
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {

         for (Point point : points) {
             canvas.drawCircle(point.x, point.y, 8, paint);
            }
    }
}
