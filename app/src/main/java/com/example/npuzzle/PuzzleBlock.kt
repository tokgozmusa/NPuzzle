package com.example.npuzzle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.content.ContextCompat

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class PuzzleBlock(val context: Context, var ID: Int, val x: Float, val y: Float, val size: Float) {

    val textSize = 25
    val strokeWidth = 3

    fun onDraw(canvas: Canvas, paint: Paint) {
        // if empty block
        if (ID == 0) {
            paint.style = Paint.Style.FILL
            paint.color = ContextCompat.getColor(context, R.color.gameBackground)
            canvas.drawRect(x, y, x + size, y + size, paint);
            return
        }

        // fill
        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(context, R.color.puzzleBlock)
        canvas.drawRect(x, y, x + size, y + size, paint);

        // text
        paint.textSize = textSize * context.resources.displayMetrics.scaledDensity
        paint.textAlign = Paint.Align.CENTER
        paint.color = ContextCompat.getColor(context, R.color.puzzleBlockText)
        canvas.drawText(ID.toString(), x + size / 2, y + size / 2 - ((paint.descent() + paint.ascent()) / 2), paint)

        //border
        paint.style = Paint.Style.STROKE
        paint.color = ContextCompat.getColor(context, R.color.gameBackground)
        paint.strokeWidth = strokeWidth * context.resources.displayMetrics.scaledDensity
        canvas.drawRect(x, y, x + size, y + size, paint);
    }


}