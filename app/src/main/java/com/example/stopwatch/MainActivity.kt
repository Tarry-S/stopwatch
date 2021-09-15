package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    //making a classwide static constant in Kotlin
    companion object {
        //all your "static" constants go here
        val TAG = "MainActivity" // val is for constants

    }

    lateinit var stopwatch : Chronometer
    lateinit var buttonStartStop : Button
    lateinit var buttonReset : Button
    lateinit var imageGrab : ImageView
    var timerStarted = false
    var pausedTime = 0.0
    var isRunning = false
    var isPaused = false
    val destructTimer = object  :CountDownTimer (100000000, 100) {
        override fun onTick(p0: Long) {
            imageGrab.scaleX += 0.1.toFloat()
            imageGrab.scaleY += 0.1.toFloat()
        }
        override fun onFinish() {
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "+++ ON CREATE +++")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wireWidgets()

        buttonStartStop.setOnClickListener {
           timerStartStop()
        }

        buttonReset.setOnClickListener {
            timerReset()
        }
    }

    private fun timerReset() {
        stopwatch.setBase(SystemClock.elapsedRealtime())
        stopwatch.stop()
        isPaused = false
        isRunning = false
        buttonStartStop.text = "start"
        destructTimer.cancel()
        imageGrab.scaleX = 1.0f
        imageGrab.scaleY = 1.0f
        imageGrab.visibility = View.GONE
    }

    private fun timerStartStop() {
        if(!isRunning){
            destructTimer.start()
            if(isPaused){
                stopwatch.setBase((stopwatch.getBase() + SystemClock.elapsedRealtime() - pausedTime).toLong())
                stopwatch.start()
                buttonStartStop.text = "stop"
                isRunning = true

            }else{
                stopwatch.setBase(SystemClock.elapsedRealtime());
                stopwatch.start()
                buttonStartStop.text = "stop"
                isRunning = true
                imageGrab.visibility = View.VISIBLE

            }
        }else{
            stopwatch.stop()
            buttonStartStop.text = "start"
            pausedTime = SystemClock.elapsedRealtime().toDouble()
            isRunning = false
            isPaused = true
            destructTimer.cancel()
        }
    }

    private fun wireWidgets() {
        stopwatch = findViewById(R.id.chronometer_main_stopwatch)
        buttonStartStop = findViewById(R.id.button_main_startStop)
        buttonReset = findViewById(R.id.button_main_reset)
        imageGrab = findViewById(R.id.imageView_main_grab)
        imageGrab.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "+++ ON START +++")
    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "+++ ON RESUME +++")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG, "+++ ON PAUSE +++")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "+++ ON STOP +++")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "+++ ON DESTROY +++")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var currentTime = SystemClock.elapsedRealtime() - stopwatch.base
        outState.putLong("savedLong", SystemClock.elapsedRealtime() - stopwatch.base)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val userLong = savedInstanceState.getLong(  "savedLong", 0)
        SystemClock.elapsedRealtime() - stopwatch.base = userLong

    }
}
