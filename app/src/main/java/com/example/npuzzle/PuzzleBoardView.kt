package com.example.npuzzle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.*

@SuppressLint("ViewConstructor")
/**
 * Created by tokgozmusa on 28/10/2017.
 */

class PuzzleBoardView(context: Context, val n: Int) : View(context) {

    val paint = Paint()

    var containerWidth: Int = 0

    var size = 0

    val mat = Array(n) { Array(n) { PuzzleBlock(context, 0, 0F, 0F, 0F) } }

    var emptyBlockIndex = Point()

    init {
        paint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        containerWidth = measuredWidth

        if(containerWidth == 0) {
            return
        }

        size = containerWidth / n
        var x = 0
        var y = 0
        var ID = 0

        val list = mutableListOf<Int>()
        for (i in 0 until n * n) {
            list.add(i, i)
        }
        Collections.shuffle(list)

        for (i in 0 until mat.size) {
            for (j in 0 until mat[0].size) {
                mat[i][j] = PuzzleBlock(context, list[ID], x.toFloat(), y.toFloat(), size.toFloat())
                if (list[ID] == 0) {
                    emptyBlockIndex = Point(i, j)
                }
                x += size
                ID++
            }
            x = 0
            y += size
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until mat.size) {
            for (j in 0 until mat[0].size) {
                mat[i][j].onDraw(canvas!!, paint)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event!!.action) {

            MotionEvent.ACTION_DOWN -> {
                Log.d("event down: ", event.x.toString() + ":" + event.y.toString())
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (size == 0) {
                    return false
                }

                val i = (event.y / size).toInt()
                val j = (event.x / size).toInt()

                if (i + 1 < n && i + 1 == emptyBlockIndex.x && j == emptyBlockIndex.y) {
                    val ID = mat[i][j].ID
                    mat[i][j].ID = 0
                    mat[i + 1][j].ID = ID
                    emptyBlockIndex = Point(i, j)
                } else if (i - 1 >= 0 && i - 1 == emptyBlockIndex.x && j == emptyBlockIndex.y) {
                    val ID = mat[i][j].ID
                    mat[i][j].ID = 0
                    mat[i - 1][j].ID = ID
                    emptyBlockIndex = Point(i, j)
                } else if (j + 1 < n && i == emptyBlockIndex.x && j + 1 == emptyBlockIndex.y) {
                    val ID = mat[i][j].ID
                    mat[i][j].ID = 0
                    mat[i][j + 1].ID = ID
                    emptyBlockIndex = Point(i, j)
                } else if (j - 1 >= 0 && i == emptyBlockIndex.x && j - 1 == emptyBlockIndex.y) {
                    val ID = mat[i][j].ID
                    mat[i][j].ID = 0
                    mat[i][j - 1].ID = ID
                    emptyBlockIndex = Point(i, j)
                }

                invalidate()

                Log.d("event up: ", event.x.toString() + ":" + event.y.toString())
            }
        }

        return super.onTouchEvent(event)
    }
}