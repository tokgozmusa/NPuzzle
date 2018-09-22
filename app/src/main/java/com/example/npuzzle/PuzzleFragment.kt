package com.example.npuzzle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_puzzle.*

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class PuzzleFragment : Fragment() {

    private val logTag = "PuzzleFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_puzzle, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        val puzzleBoardView = PuzzleBoardView(context, mainActivity.n)
        puzzle_container.addView(puzzleBoardView)

        button_new_game.setOnClickListener {
            puzzleBoardView.initGame()
            puzzleBoardView.invalidate()
        }
    }
}
