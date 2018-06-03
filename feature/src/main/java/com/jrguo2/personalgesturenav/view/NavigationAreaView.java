package com.jrguo2.personalgesturenav.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;

import com.jrguo2.personalgesturenav.utils.Configs;

public class NavigationAreaView extends View {

    private Paint paint;
    private boolean show;
    private int pillColor;
    private int outlineColor;
    private float height;
    private float width;
    private float roundRadius;
    private float outlineRadius;

    public NavigationAreaView(Context context) {
        super(context);

        paint = new Paint();
        show = true;

        updateParameters();
    }

    public void updateParameters(){
        width = Configs.getFloat("navBarWidth", 100f);
        height = Configs.getFloat("navBarHeight", 40f);
        roundRadius = Configs.getFloat("navBarRadius", 50f);
        outlineRadius = Configs.getFloat("navBarOutlineThickness", 1f);
        String color = Configs.getString("pillColor","#FFFFFF");
        pillColor = Color.parseColor(color);
        outlineColor = Color.parseColor(Configs.getString("outlineColor","#FFFFFF"));
    }

    @Override
    public void onDraw(Canvas canvas) {
        //Clear the screen
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (show) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(pillColor);
            canvas.drawRoundRect(0, 0, canvas.getWidth(), height, roundRadius, roundRadius, paint);

//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(outlineRadius);
//            paint.setColor(outlineColor);
//            float half = outlineRadius /2;
            //canvas.drawRoundRect( half, half, canvas.getWidth() - half, height - half, roundRadius, roundRadius, paint);
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
