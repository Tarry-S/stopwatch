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
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    //making a classwide static constant in Kotlin
    companion object {
        //all your "static" constants go here
        const val TAG = "MainActivity" // val is for constants
        const val BUNDLE_PAUSED_TIME = "paused time"
        const val BUNDLE_BASE_TIME = "base time"

    }

    lateinit var stopwatch : Chronometer
    lateinit var buttonStartStop : Button
    lateinit var buttonReset : Button
    lateinit var imageGrab : ImageView
    var pausedTime = 0L
    var baseTime = 0L
    var isRunning = false
    var isPaused = false
    private val destructTimer = object  :CountDownTimer (100000000, 100) {
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

        //check savedInstanceState for a value and assign if necessary
        // ?. operator is a null-safe access of the object. Acts like an if blah != null
        //blah!!. asserts that blah isn't null and will crash if it is
        // in case the stuff on the left is null and can't be accessed
        pausedTime = savedInstanceState?.getLong(BUNDLE_PAUSED_TIME) ?: 0L
        baseTime = savedInstanceState?.getLong(BUNDLE_BASE_TIME) ?: 0L


        if(isPaused){
            stopwatch.base = (stopwatch.base + SystemClock.elapsedRealtime() - pausedTime)
        }
        stopwatch.base = baseTime
        stopwatch.start()


        buttonStartStop.setOnClickListener {
           timerStartStop()
        }

        buttonReset.setOnClickListener {
            timerReset()
        }
    }

    private fun updateBase() {
        if(isRunning){
            baseTime =  stopwatch.base
        }
    }

    private fun timerReset() {
        stopwatch.base = SystemClock.elapsedRealtime()
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
                stopwatch.base = (stopwatch.base + SystemClock.elapsedRealtime() - pausedTime)
                stopwatch.start()
                buttonStartStop.text = "stop"
                isRunning = true

            }else{
                stopwatch.base = SystemClock.elapsedRealtime();
                stopwatch.start()
                buttonStartStop.text = "stop"
                isRunning = true
                imageGrab.visibility = View.VISIBLE

            }
        }else{
            stopwatch.stop()
            buttonStartStop.text = "start"
            pausedTime = SystemClock.elapsedRealtime()
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
        // a bundle lets you store key value pairs
        //Key can be any unique text
        updateBase()
        outState.putLong(BUNDLE_BASE_TIME, baseTime)
        outState.putLong(BUNDLE_PAUSED_TIME, pausedTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }
}
