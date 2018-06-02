package com.jrguo2.personalgesturenav.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;

import com.jrguo2.personalgesturenav.utils.Configs;

public class AreaView extends View {

    private Paint paint;
    private boolean show;
    private Color pillColor;
    private Color outlineColor;
    private float height;
    private float width;
    private float roundRadius;

    public AreaView(Context context) {
        super(context);

        paint = new Paint();
        show = true;

        pillColor = Color.valueOf(0f, 0f, 0f, 0.35f);
        outlineColor = Color.valueOf(1f, 1f, 1f, .75f);

        updateParameters();
    }

    public void updateParameters(){
        width = Configs.getFloat("navBarWidth", 100f);
        height = Configs.getFloat("navBarHeight", 40f);
        roundRadius = Configs.getFloat("navBarRadius", 50f);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //Clear the screen
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (show) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(pillColor.toArgb());
            canvas.drawRoundRect(0, 0, canvas.getWidth(), height, roundRadius, roundRadius, paint);

//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(1);
//            paint.setColor(pillColor.toArgb());
//            canvas.drawRoundRect(0, 0, canvas.getWidth(), height, roundRadius, roundRadius, paint);
        }
    }

    public void showArea(){
        this.show = true;
        this.invalidate();
    }

    public void hide(){
        this.show = false;
        this.invalidate();
    }
}
