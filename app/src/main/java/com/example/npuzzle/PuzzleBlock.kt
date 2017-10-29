package com.example.npuzzle

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class PuzzleBlock(var ID: Int, val x: Float, val y: Float, val size: Float) {

    constructor() : this(0, 0F, 0F, 0F)

    fun onDraw(canvas: Canvas, paint: Paint) {
        // if empty block
        if(ID == 0) {
            paint.style = Paint.Style.FILL
            paint.color = Color.parseColor("#FAFAFA")
            canvas.drawRect(x, y, x + size, y + size, paint);
            return
        }

        // fill
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#BBDEFB")
        canvas.drawRect(x, y, x + size, y + size, paint);

        // text
        paint.textSize = 100F
        paint.textAlign = Paint.Align.CENTER
        paint.color = Color.BLUE
        canvas.drawText(ID.toString(), x + size / 2, y + size / 2, paint)

        //border
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#FAFAFA")
        paint.strokeWidth = 20F
        canvas.drawRect(x, y, x + size, y + size, paint);
    }


}