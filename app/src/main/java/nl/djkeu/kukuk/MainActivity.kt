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

        val scope = MainScope() // could also use an other scope such as viewModelScope if available
        var job: Job? = null

        fun runAlarms()  {
            fun stopAlarms() {
                job?.cancel()
                job = null
            }

            stopAlarms()
            job = scope.launch {
                while (true) {
                    quarterlyAlarms()
                    hourlyAlarms()
                    //minutelyAlarms()
                    delay(1000)
                }
            }
        }

        runBlocking {
            runAlarms()
        }

    }

    // Play and show kuku once
    private fun kukuOnce() {
        // Set kuku sound
        val resourceId = resources.getIdentifier("keukuk", "raw", packageName)
        val kukuPlayer = MediaPlayer.create(this, resourceId)

        // Show kuku
        val resultTextView: TextView = findViewById(R.id.textView2)
        resultTextView.text = getString(R.string.kukukTextView)

        kukuPlayer.start()
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

    // Call kukuOnce() every 15 minutes
    private fun quarterlyAlarms() {
        val getCurrentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)

        val quarters = arrayOf( "15:00", "30:00", "45:00" )

        if (currentTime in quarters) {
            kukuOnce()
        }
    }

    // Call kukuTimes every hour
    private fun hourlyAlarms() {
        val getCurrentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)


        for (i in 1..24) {
            fun checkHours(times: Int) {
                val formattedHour = String.format("%02d", i)
                val hour = "${formattedHour}:00:00"

                if (hour == currentTime) {
                    kukuTimes(times)
                }
            }

            val times = if (i < 13) {
                i - 0
            } else {
                i - 12
            }
            checkHours(times)
        }
    }

    // Call kukuTimes every minute
    /*
    private fun minutelyAlarms() {
        val getCurrentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)

        for (i in 1..59) {
            fun checkMinutes(times: Int) {
                val formattedMinute = String.format("%02d", i)
                val minute = "${formattedMinute}:00"

                if (minute == currentTime) {
                    kukuTimes(times)
                }
            }

            val times = if (i < 11) {
                i - 0
            } else if (i < 21) {
                i - 10
            } else if (i < 31) {
                i - 20
            } else if (i < 41) {
                i - 30
            } else if (i < 51) {
                i - 40
            } else {
                i - 50
            }
            checkMinutes(times)
        }
    }

     */
}

