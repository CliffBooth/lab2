package com.example.lab2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Continuewatch1 : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView

    private var counting = false
    var threadExists = true

    var prevTime: Long = System.currentTimeMillis()
    var pauseTime: Long = 0
    var resumeTime: Long = 0

    var backgroundThread = Thread {
        while (threadExists) {
            val newTime = System.currentTimeMillis()
            if (counting) {
                if (newTime - (resumeTime - pauseTime) - prevTime >= 1000) {
                    textSecondsElapsed.post {
                        textSecondsElapsed.text =
                            getString(R.string.secondsElapsed, ++secondsElapsed)
                    }
                    prevTime = newTime
                    resumeTime = 0L
                    pauseTime = 0L
                }
            }
        }
    }

    companion object {
        const val STATE_SECONDS = "secondsElapsed"
        const val PAUSE_TIME = "pause"
        const val PREV_TIME = "prev"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.continuewatch)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SECONDS, secondsElapsed)
        outState.putLong(PAUSE_TIME, pauseTime)
        outState.putLong(PREV_TIME, prevTime)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt(STATE_SECONDS)
        pauseTime = savedInstanceState.getLong(PAUSE_TIME)
        prevTime = savedInstanceState.getLong(PREV_TIME)
    }

    override fun onPause() {
        super.onPause()
        pauseTime = System.currentTimeMillis()
        counting = false
    }

    override fun onResume() {
        super.onResume()
        if (pauseTime != 0L) {
            textSecondsElapsed.post {
                textSecondsElapsed.text = getString(R.string.secondsElapsed, secondsElapsed)
            }
            resumeTime = System.currentTimeMillis()
        }
        counting = true
    }

    override fun onDestroy() {
        super.onDestroy()
        threadExists = false
        backgroundThread.join()
    }

}