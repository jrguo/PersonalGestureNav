package com.jrguo2.personalgesturenav.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Code derived from:
 * https://stackoverflow.com/questions/3972445/how-to-put-text-in-a-drawable
 */
public class TextDrawable extends Drawable {

    private String text;
    private final Paint paint;
    private int xOffset;
    private int yOffset;
    private float radius;

    public TextDrawable(String text) {

        this.text = text;

        this.paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(22f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);

        xOffset = 0;
        yOffset = 0;
        radius = 24f;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setXOffset(int offset){
        this.xOffset = offset;
    }

    public void setyOffset(int offset){
        this.yOffset = offset;
    }

    public void setOffsets(int x, int y){
        this.xOffset = x;
        this.yOffset = y;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawCircle(xOffset - (radius / 2), yOffset + (radius / 2), radius, paint);

        paint.setTextSize(20f);
        paint.setColor(Color.BLACK);
        canvas.drawText(text, xOffset - 30f, yOffset - 20f, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}