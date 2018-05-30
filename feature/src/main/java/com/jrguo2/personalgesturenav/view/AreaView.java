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

        //Draw some rectangles and stuff
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(30 + xOffset, 30 + yOffset, 80 + xOffset, 80 + yOffset, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33 + xOffset, 60 + yOffset, 77 + xOffset, 77 + yOffset, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33 + xOffset, 33 + yOffset, 77 + xOffset, 60 + yOffset, paint );

        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }
}
