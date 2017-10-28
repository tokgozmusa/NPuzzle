package com.example.npuzzle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class MainActivity : AppCompatActivity() {

    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        showMainFragment()

        button_start.setOnClickListener {
            showPuzzleFragment()
        }
    }

    fun hideAppTitle() {
        this.getSupportActionBar()!!.hide();
    }

    fun hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    fun hideNavigationBar() {
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
    }

    fun showMainFragment() {
        val transaction = manager.beginTransaction();
        val fragment = MainFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    fun showPuzzleFragment() {
        val transaction = manager.beginTransaction();
        val fragment = PuzzleFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}
