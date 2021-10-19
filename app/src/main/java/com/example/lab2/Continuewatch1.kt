package com.example.lab2

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Continuewatch1 : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView

    var running = true
    val backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (running) {
                textSecondsElapsed.post {
                    textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                }
            }
        }
    }

    companion object {
        val STATE_SECONDS = "secondsElapsed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.continuewatch)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SECONDS, secondsElapsed)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt(STATE_SECONDS)
    }

    override fun onPause() {
        super.onPause()
        running = false
    }

    override fun onResume() {
        super.onResume()
        running = true
    }

}