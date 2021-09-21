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
        const val BUNDLE_IS_RUNNING = "is running"
        const val BUNDLE_IS_PAUSED = "is paused"

    }

    lateinit var stopwatch : Chronometer
    lateinit var buttonStartStop : Button
    lateinit var buttonReset : Button
    var pausedTime = 0L
    var baseTime = 0L
    var isRunning = false
    var isPaused = false


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
        isRunning = savedInstanceState?.getBoolean(BUNDLE_IS_RUNNING) ?: false
        isPaused = savedInstanceState?.getBoolean(BUNDLE_IS_PAUSED) ?: false


        Log.d(TAG, "onCreate: pause: $pausedTime base: $baseTime")

        if(!isRunning){
            if(isPaused){
                stopwatch.base = (baseTime + SystemClock.elapsedRealtime() - pausedTime)
            }else{
                stopwatch.base = SystemClock.elapsedRealtime()
                stopwatch.stop()
            }
        }else{
            stopwatch.base = baseTime
            stopwatch.start()
            buttonStartStop.text = "stop"
        }
        Log.d(TAG, "onCreate after logic: pause: $pausedTime base: $baseTime")

        buttonStartStop.setOnClickListener {
           timerStartStop()
        }

        buttonReset.setOnClickListener {
            timerReset()
        }
    }

    private fun updateBase() {
            baseTime =  stopwatch.base
    }

    private fun timerReset() {
        stopwatch.base = SystemClock.elapsedRealtime()
        stopwatch.stop()
        isPaused = false
        isRunning = false
        buttonStartStop.text = "start"
    }

    private fun timerStartStop() {
        if(!isRunning){
            if(isPaused){
                stopwatch.base = (baseTime + SystemClock.elapsedRealtime() - pausedTime)
                stopwatch.start()
                buttonStartStop.text = "stop"
                isRunning = true

            }else{
                stopwatch.base = SystemClock.elapsedRealtime();
                stopwatch.start()
                buttonStartStop.text = "stop"
                isRunning = true

            }
        }else{
            stopwatch.stop()
            buttonStartStop.text = "start"
            pausedTime = SystemClock.elapsedRealtime()
            isRunning = false
            isPaused = true
        }
    }

    private fun wireWidgets() {
        stopwatch = findViewById(R.id.chronometer_main_stopwatch)
        buttonStartStop = findViewById(R.id.button_main_startStop)
        buttonReset = findViewById(R.id.button_main_reset)
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
        outState.putBoolean(BUNDLE_IS_RUNNING, isRunning)
        outState.putBoolean(BUNDLE_IS_PAUSED, isPaused)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }
}
