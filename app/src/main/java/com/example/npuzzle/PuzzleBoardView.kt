package com.example.npuzzle

import android.annotation.SuppressLint
import android.app.FragmentManager
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.MotionEvent
import android.view.View

@SuppressLint("ViewConstructor")
/**
 * Created by tokgozmusa on 28/10/2017.
 */

class PuzzleBoardView(context: Context, val n: Int) : View(context) {

    private val paint = Paint()

    private var containerWidth: Int = 0

    private var size = 0

    private val mat = Array(n) { Array(n) { PuzzleBlock(context, 0, 0F, 0F, 0F) } }

    private var emptyBlockIndex = Point(n - 1, n - 1)

    init {
        paint.isAntiAlias = true
    }

    fun initGame() {
        emptyBlockIndex = Point(n - 1, n - 1)
        size = containerWidth / n
        var x = 0
        var y = 0
        var ID = 1
        for (i in 0 until mat.size) {
            for (j in 0 until mat[0].size) {
                mat[i][j] = PuzzleBlock(context, ID, x.toFloat(), y.toFloat(), size.toFloat())
                ID++
                ID %= n * n
                x += size
            }
            x = 0
            y += size
        }
        shuffleMat()
    }

    private fun shuffleMat() {
        val iteration = 100
        for (i in 0 until iteration) {
            val options = mutableListOf<Point>()
            if (emptyBlockIndex.x + 1 < n) {
                options.add(Point(emptyBlockIndex.x + 1, emptyBlockIndex.y))
            }
            if (emptyBlockIndex.x - 1 >= 0) {
                options.add(Point(emptyBlockIndex.x - 1, emptyBlockIndex.y))
            }
            if (emptyBlockIndex.y + 1 < n) {
                options.add(Point(emptyBlockIndex.x, emptyBlockIndex.y + 1))
            }
            if (emptyBlockIndex.y - 1 >= 0) {
                options.add(Point(emptyBlockIndex.x, emptyBlockIndex.y - 1))
            }
            options.shuffle()
            val selectedIndex = options[0]
            swapBlock(selectedIndex.x, selectedIndex.y)
        }
    }

    private fun isSolution(): Boolean {
        var count = 1
        for (i in 0 until mat.size) {
            for (j in 0 until mat[0].size) {
                if (mat[i][j].ID != count && count != n * n) {
                    return false
                }
                count++
            }
        }
        return true
    }

    private fun swapBlock(i: Int, j: Int) {
        val ID = mat[i][j].ID
        mat[i][j].ID = 0
        mat[emptyBlockIndex.x][emptyBlockIndex.y].ID = ID
        emptyBlockIndex = Point(i, j)
    }

    private fun makeMove(i: Int, j: Int) {
        swapBlock(i, j)
        invalidate()
        if (isSolution()) {
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Congratulations!")
            alertDialog.setCancelable(false)
            alertDialog.setMessage("Do you want to play again?")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { dialog, _ ->
                // refresh the game
                initGame()
                invalidate()
                dialog.dismiss()
            }
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, _ ->
                // return to main menu
                (context as MainActivity).fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                dialog.dismiss()
            }
            alertDialog.show()
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        containerWidth = measuredWidth

        if (containerWidth == 0) {
            return
        }

        initGame()
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
                    makeMove(i, j)
                } else if (i - 1 >= 0 && i - 1 == emptyBlockIndex.x && j == emptyBlockIndex.y) {
                    makeMove(i, j)
                } else if (j + 1 < n && i == emptyBlockIndex.x && j + 1 == emptyBlockIndex.y) {
                    makeMove(i, j)
                } else if (j - 1 >= 0 && i == emptyBlockIndex.x && j - 1 == emptyBlockIndex.y) {
                    makeMove(i, j)
                }

                Log.d("event up: ", event.x.toString() + ":" + event.y.toString())
            }
        }

        return super.onTouchEvent(event)
    }
}
