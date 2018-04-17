package com.example.android.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 5x5 Single player Board
 */
public class BoardView5x5 extends View {
    private static final int LINE_THICK = 10;
    private static final int ELT_MARGIN = 20;
    private static final int ELT_STROKE_WIDTH = 25;
    private int width, height, eltW, eltH;
    private Paint gridPaint, oPaint, xPaint;
    private GameEngine5x5 gameEngine5x5;
    private MainActivity5x5 activity;

    public BoardView5x5(Context context) {
        super(context);
    }
    public BoardView5x5(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridPaint = new Paint();
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.WHITE);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(ELT_STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.WHITE);
    }
    public void setMainActivity(MainActivity5x5 a) {
        activity = a;
    }

    public void setGameEngine5x5(GameEngine5x5 g) {
        gameEngine5x5 = g;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        eltW = (width - LINE_THICK) / 5;
        eltH = (height - LINE_THICK) / 5;

        setMeasuredDimension(width, height);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBoard(canvas);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameEngine5x5.isEnded()  &&  event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / eltW);
            int y = (int) (event.getY() / eltH);
            char win = gameEngine5x5.play(x, y);
            invalidate();

            if (win != ' ') {
                activity.gameEnded(win);
            } else {
                // computer plays ...
                win = gameEngine5x5.computer();
                invalidate();

                if (win != ' ') {
                    activity.gameEnded(win);
                }
            }
        }

        return super.onTouchEvent(event);
    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                drawElt(canvas, gameEngine5x5.getElt(i, j), i, j);
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 4; i++) {
            // vertical lines
            float left = eltW * (i + 1);
            float right = left + LINE_THICK;
            float top = 0;
            float bottom = height;

            canvas.drawRect(left, top, right, bottom, gridPaint);

            // horizontal lines
            float left2 = 0;
            float right2 = width;
            float top2 = eltH * (i + 1);
            float bottom2 = top2 + LINE_THICK;

            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
        }
    }
    private void drawElt(Canvas canvas, char c, int x, int y) {
        if (c == 'O') {
            float cx = (eltW * x) + eltW / 2;
            float cy = (eltH * y) + eltH / 2;

            canvas.drawCircle(cx, cy, Math.min(eltW, eltH) / 2 - ELT_MARGIN * 2, oPaint);

        } else if (c == 'X') {
            float startX = (eltW * x) + ELT_MARGIN;
            float startY = (eltH * y) + ELT_MARGIN;
            float endX = startX + eltW - ELT_MARGIN * 2;
            float endY = startY + eltH - ELT_MARGIN;

            canvas.drawLine(startX, startY, endX, endY, xPaint);

            float startX2 = (eltW * (x + 1)) - ELT_MARGIN;
            float startY2 = (eltH * y) + ELT_MARGIN;
            float endX2 = startX2 - eltW + ELT_MARGIN * 2;
            float endY2 = startY2 + eltH - ELT_MARGIN;

            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
        }
    }
}