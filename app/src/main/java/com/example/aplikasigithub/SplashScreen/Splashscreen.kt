package com.example.aplikasigithub.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import com.example.aplikasigithub.MainActivity
import com.example.aplikasigithub.R

class Splashscreen : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        progressBar = findViewById(R.id.progressBarscreen)

        val splashScreenDuration = 3000L


        val progressBarIncrement = 100


        val totalSteps = (splashScreenDuration / progressBarIncrement).toInt()


        val handler = Handler(Looper.getMainLooper())

        handler.post(object : Runnable {
            private var progress = 0

            override fun run() {
                if (progress < totalSteps) {
                    progress++
                    progressBar.progress = (progress * 100) / totalSteps
                    handler.postDelayed(this, progressBarIncrement.toLong())
                } else {
                    // Ketika ProgressBar mencapai 100%, pindah ke MainActivity
                    val intent = Intent(this@Splashscreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }
}