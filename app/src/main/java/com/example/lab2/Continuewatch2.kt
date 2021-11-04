package com.example.lab2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Continuewatch2 : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    lateinit var sharedPreference: SharedPreferences

    companion object {
        const val STATE_SECONDS = "secondsElapsed"
        const val PAUSE_TIME = "pause"
        const val PREV_TIME = "prev"
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.continuewatch)
        sharedPreference = getPreferences(MODE_PRIVATE)
        secondsElapsed = sharedPreference.getInt(STATE_SECONDS, 0)
        pauseTime = sharedPreference.getLong(PAUSE_TIME, 0)
        prevTime = sharedPreference.getLong(PREV_TIME, 0)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onPause() {
        super.onPause()
        counting = false
        pauseTime = System.currentTimeMillis()
        with(sharedPreference.edit()) {
            putInt(STATE_SECONDS, secondsElapsed)
            putLong(PAUSE_TIME, pauseTime)
            putLong(PREV_TIME, prevTime)
            apply()
        }
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