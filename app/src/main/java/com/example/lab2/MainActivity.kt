package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    val tag = "LIFECYCLE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(tag, "created")
    }

    override fun onStart() {
        super.onStart()
        Log.i(tag, "started")
    }

    override fun onPause() {
        super.onPause()
        Log.i(tag, "paused")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag, "resumed")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(tag, "restarted")
    }

    override fun onStop() {
        super.onStop()
        Log.i(tag, "stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "destroyed")
    }
}