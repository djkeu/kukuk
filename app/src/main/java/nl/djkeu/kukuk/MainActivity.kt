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
            if (i < 13) {
                val times = i - 0
                val formattedHour = String.format("%02d", i)
                val hour = "${formattedHour}:00:00"

                if (hour == currentTime) {
                    kukuTimes(times)
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

