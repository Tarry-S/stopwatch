package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    //making a classwide static constant in Kotlin
    companion object {
        //all your "static" constants go here
        val TAG = "MainActivity" // val is for constants
        val VERBOSE = false

    }

    lateinit var stopwatch : Chronometer
    lateinit var buttonStartStop : Button
    lateinit var buttonReset : Button
    var timerStarted = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wireWidgets()
    }

    private fun wireWidgets() {
        stopwatch = findViewById(R.id.chronometer_main_stopwatch)
        buttonStartStop = findViewById(R.id.button_main_startStop)
        buttonReset = findViewById(R.id.button_main_reset)
    }

    override fun onStart() {
        super.onStart()
        if (VERBOSE) Log.v(TAG, "+++ ON START +++")
    }

    override fun onResume() {
        super.onResume()
        if (VERBOSE) Log.v(TAG, "+++ ON RESUME +++")
    }

    override fun onPause() {
        super.onPause()
        if (VERBOSE) Log.v(TAG, "+++ ON PAUSE +++")
    }

    override fun onStop() {
        super.onStop()
        if (VERBOSE) Log.v(TAG, "+++ ON STOP +++")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (VERBOSE) Log.v(TAG, "+++ ON DESTROY +++")
    }
}