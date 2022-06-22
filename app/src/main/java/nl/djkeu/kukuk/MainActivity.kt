package nl.djkeu.kukuk

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        suspend fun runAlarms() = withContext(Dispatchers.IO) {
            launch {
                    minutelyAlarms()
                    delay(1000)
            }

            launch {
                quarterlyAlarms()
                delay(1000)
            }

            launch {
                    hourlyAlarms()
                    delay(1000)
            }
        }

        runBlocking {
               runAlarms()
        }
        */
}       




        // Play and show kuku once
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
        private fun kukuTimes(times: Int) {
            for (i in 1..times) {
                kukuOnce()
                Thread.sleep(1000)
            }
        }

        // Refactor
        private fun minutelyAlarms() {
            // For testing, to be removed when the alarms work
            val getCurrentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
            val currentTime = formatter.format(getCurrentTime)

            for (i in 1..59) {
                if (i < 11) {
                    val formattedMinute = String.format("%02d", i)
                    val hour = "${formattedMinute}:00"

                    if (hour == currentTime) {
                        kukuTimes(i)
                    }
                } else if (i < 21) {
                    val times = i - 10
                    val formattedMinute = String.format("%02d", i)
                    val hour = "${formattedMinute}:00"

                    if (hour == currentTime) {
                        kukuTimes(times)
                    }
                } else if (i < 31) {
                    val times = i - 20
                    val formattedMinute = String.format("%02d", i)
                    val hour = "${formattedMinute}:00"

                    if (hour == currentTime) {
                        kukuTimes(times)
                    }
                } else if (i < 41) {
                    val times = i - 30
                    val formattedMinute = String.format("%02d", i)
                    val hour = "${formattedMinute}:00"

                    if (hour == currentTime) {
                        kukuTimes(times)
                    }
                } else if (i < 51) {
                    val times = i - 40
                    val formattedMinute = String.format("%02d", i)
                    val hour = "${formattedMinute}:00"

                    if (hour == currentTime) {
                        kukuTimes(times)
                    }
                } else {
                    val times = i - 40
                    val formattedMinute = String.format("%02d", i)
                    val hour = "${formattedMinute}:00"

                    if (hour == currentTime) {
                        kukuTimes(times)
                    }
                }

            }
        }


        /* Backup
        private fun minutelyAlarms() {
            // For testing, to be removed when the alarms work
            val getCurrentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("ss", Locale.getDefault())
            val currentTime = formatter.format(getCurrentTime)

            // val minutes = arrayOf("05", "10", "20",
            //            "25", "35", "40", "50", "55")

            val minutes = arrayOf("05", "15", "25",
                "35", "45", "55")

            // val minutes = arrayOf("10", "25",
            //    "40", "55")

            if (currentTime in minutes) {
                kukuOnce()
            }
        }
        */




        private fun quarterlyAlarms() {
                    val getCurrentTime = Calendar.getInstance().time
                    val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
                    val currentTime = formatter.format(getCurrentTime)

                    val quarters = arrayOf("15:00", "30:00", "45:00")

                    if (currentTime in quarters) {
                        kukuOnce()
                    }
                }


        private fun hourlyAlarms() {
            val getCurrentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
            val currentTime = formatter.format(getCurrentTime)


            for (i in 1..24) {
                if (i < 13) {
                    val formattedHour = String.format("%02d", i)
                    val hour = "${formattedHour}:00:00"

                    if (hour == currentTime) {
                        kukuTimes(i)
                    }
                } else {
                    val times = i - 12
                    val formattedHour = String.format("%02d", times)
                    val hour = "${formattedHour}:00:00"

                    if (hour == currentTime) {
                        kukuTimes(times)
                    }
                }
            }
        }
}

