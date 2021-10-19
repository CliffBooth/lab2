package com.example.lab2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Continuewatch2 : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    lateinit var sharedPreference: SharedPreferences
    var running = true

    companion object {
        val STATE_SECONDS = "secondsElapsed"
    }

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (running) {
                textSecondsElapsed.post {
                    textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.continuewatch)
        running = true
        sharedPreference = getPreferences(MODE_PRIVATE)
        secondsElapsed = sharedPreference.getInt(STATE_SECONDS, 0)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onPause() {
        super.onPause()
        running = false
        with(sharedPreference.edit()) {
            putInt(STATE_SECONDS, secondsElapsed)
            apply()
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
    }
}