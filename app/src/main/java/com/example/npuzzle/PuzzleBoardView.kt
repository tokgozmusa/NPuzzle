package com.example.npuzzle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class PuzzleBoardView : View {
    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var paint = Paint()
        paint.color = Color.BLUE
        canvas!!.drawRect(30F, 50F, 200F, 350F, paint);
    }
}