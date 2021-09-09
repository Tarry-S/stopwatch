package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    //making a classwide static constant in Kotlin
    companion object {
        //all your "static" constants go here
        val TAG = "MainActivity" // val is for constants
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}