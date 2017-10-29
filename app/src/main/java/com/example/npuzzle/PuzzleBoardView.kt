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

    fun initGame() {
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        containerWidth = measuredWidth

        if (containerWidth == 0) {
            return
        }

        initGame()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until mat.size) {
            for (j in 0 until mat[0].size) {
                mat[i][j].onDraw(canvas!!, paint)
            }
        }
    }

    fun isSolution(): Boolean {
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

    fun moveBlock(i: Int, j: Int) {
        val ID = mat[i][j].ID
        mat[i][j].ID = 0
        mat[emptyBlockIndex.x][emptyBlockIndex.y].ID = ID
        emptyBlockIndex = Point(i, j)
        invalidate()
        if (isSolution()) {
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Congratulations!")
            alertDialog.setCancelable(false)
            alertDialog.setMessage("Do you want to play again?")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", { dialog, which ->
                // refresh the game
                initGame()
                invalidate()
                dialog.dismiss()
            })
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", { dialog, which ->
                // return to main menu
                (context as MainActivity).manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                dialog.dismiss()
            })
            alertDialog.show()
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
                    moveBlock(i, j)
                } else if (i - 1 >= 0 && i - 1 == emptyBlockIndex.x && j == emptyBlockIndex.y) {
                    moveBlock(i, j)
                } else if (j + 1 < n && i == emptyBlockIndex.x && j + 1 == emptyBlockIndex.y) {
                    moveBlock(i, j)
                } else if (j - 1 >= 0 && i == emptyBlockIndex.x && j - 1 == emptyBlockIndex.y) {
                    moveBlock(i, j)
                }

                Log.d("event up: ", event.x.toString() + ":" + event.y.toString())
            }
        }

        return super.onTouchEvent(event)
    }
}