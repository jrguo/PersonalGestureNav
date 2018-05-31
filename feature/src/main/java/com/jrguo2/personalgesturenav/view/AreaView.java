package com.jrguo2.personalgesturenav.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;

public class AreaView extends View {

    Paint paint;
    int xOffset;
    int yOffset;
    int dir;

    public AreaView(Context context) {
        super(context);

        paint = new Paint();

        xOffset = 50;
        yOffset = 0;

        dir = 1;


    }

    @Override
    public void onDraw(Canvas canvas) {
        //Clear the screen
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        xOffset += dir;
        yOffset += dir;

        if(xOffset < 0 || xOffset > 700){
            dir = -dir;
        }

        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), canvas.getHeight(), canvas.getHeight(), paint);
    }
}
