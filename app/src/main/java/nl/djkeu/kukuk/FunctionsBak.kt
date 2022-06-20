package nl.djkeu.kukuk

import android.widget.Button
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class FunctionsBak {


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

    // Button for testing purposes, to be removed when coroutines work
    val kukuButton: Button = findViewById(R.id.button)
    kukuButton.setOnClickListener {
        kukuOnce()
    }


    launch {
        minutelyAlarms()
        delay(1000)
    }





}