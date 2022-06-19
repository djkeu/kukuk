package nl.djkeu.kukuk

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button for testing purposes, to be removed when coroutines work
        val kukuButton: Button = findViewById(R.id.button)
        kukuButton.setOnClickListener {
            kukuOnce()
        }

        // Run all alarms defined in the functions below
        runBlocking {
            // Not looping
            minutelyAlarms()
            quarterlyAlarms()
            //kukuOnce()
        }

        /*
        // Crashes the app
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                kukuOnce()
            }
        }, 5000, 5000)
        */
}


        private fun kukuOnce() {
            // Play kuku sound
            val resId = resources.getIdentifier("keukuk", "raw", packageName)
            val mediaPlayer = MediaPlayer.create(this, resId)

            mediaPlayer.start()

            // Show kuku
            val resultTextView: TextView = findViewById(R.id.textView2)
            resultTextView.text = getString(R.string.kukukTextView)

            Handler(Looper.getMainLooper()).postDelayed(
                { resultTextView.text = "" },
                1200
            )
        }


        // Play and show kuku multiple times
        fun kukuTimes(times: Int) {
            for (i in 1..times) {
                kukuOnce()
            }
        }


        private fun minutelyAlarms() {
            // For testing, to be removed when the alarms work
            val getCurrentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("ss", Locale.getDefault())
            val currentTime = formatter.format(getCurrentTime)

            val minutes = arrayOf("00", "05", "10", "20",
                        "25", "35", "40", "50", "55")

            if (currentTime in minutes) {
                kukuOnce()
            }
        }


        private fun quarterlyAlarms() {
            val getCurrentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
            val currentTime = formatter.format(getCurrentTime)

            val quarters = arrayOf("15:00", "30:00", "45:00")

            if (currentTime in quarters) {
                kukuOnce()
            }
        }


        /*
        // Obsolete, using direct calls to the ...Alarms() instead
        private fun runAlarms() {
            minutelyAlarms()
            quarterlyAlarms()
        }
        */
}

