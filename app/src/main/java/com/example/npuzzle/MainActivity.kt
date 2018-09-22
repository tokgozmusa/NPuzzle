package com.example.npuzzle

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class MainActivity : AppCompatActivity() {

    private val logTag = "MainFragment"

    val fragmentManager: FragmentManager = supportFragmentManager

    var n = 3 // size of the puzzle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "onCreate")
        setContentView(R.layout.activity_main)
        showMainFragment()
    }

    private fun showMainFragment() {
        val transaction = fragmentManager.beginTransaction()
        val fragment = MainFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    fun showPuzzleFragment(newN: Int) {
        n = newN
        val transaction = fragmentManager.beginTransaction()
        val fragment = PuzzleFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
