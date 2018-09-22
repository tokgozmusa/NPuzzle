package com.example.npuzzle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class MainFragment : Fragment() {

    private val logTag = "MainFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity: MainActivity = activity as MainActivity
        button_three_x_three.setOnClickListener {
            mainActivity.showPuzzleFragment(3)
        }
        button_four_x_four.setOnClickListener {
            mainActivity.showPuzzleFragment(4)
        }
        button_five_x_five.setOnClickListener {
            mainActivity.showPuzzleFragment(5)
        }
    }
}
