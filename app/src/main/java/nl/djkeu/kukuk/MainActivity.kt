package nl.djkeu.kukuk

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val kukuScope = MainScope()
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runBlocking {
            stopAlarms()
            startAlarms()
        }
    }

    // Start and stop the alarms
    private fun stopAlarms() {
        job?.cancel()
        job = null
    }

    private fun startAlarms() {
        job = kukuScope.launch {
            loopSelectedAlarms()
        }
    }


    // Loop selected alarms
    private suspend fun loopSelectedAlarms() {
        while (true) {
            delay(1000)  // Needed to start the UI

            // Select alarms
            quarterlyAlarms()
            hourlyAlarms()
            // minutelyAlarms()
        }
    }


    // delay function for kukuTextOnce() and kukuSoundOnce()
    private suspend fun delayWithMillis(millis: Long) = withContext(Dispatchers.Default) {
        delay(millis)
    }

    // Show kuku text once
    private suspend fun kukuTextOnce() {
        // Set kuku text
        val resultTextView: TextView = findViewById(R.id.textView2)
        resultTextView.text = getString(R.string.kukukTextView)

        // Reset kuku text
        Handler(Looper.getMainLooper()).postDelayed(
            { resultTextView.text = "" },
            1000
        )
        delayWithMillis(200)
    }


    // Play kuku sound once
    private suspend fun kukuSoundOnce() {
        val resourceId = resources.getIdentifier("keukuk", "raw", packageName)
        val kukuPlayer = MediaPlayer.create(this, resourceId)

        kukuPlayer.start()
        delayWithMillis(1200)
        kukuPlayer.release()
    }


    // Kuku multiple times
    private suspend fun kukuMultipleTimes(times: Int) {
        for (i in 1..times) {
            kukuTextOnce()
            kukuSoundOnce()
        }
    }


    // Quarterly alarms
    @Suppress("unused")
    private suspend fun quarterlyAlarms() {
        // val getCurrentTime = Calendar.getInstance().time
        val getCurrentTime = Date()
        val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)

        val quarters = arrayOf( "15:00", "30:00", "45:00" )
        // TEST: val quarters = arrayOf( "05:00", "10:00", "15:00", "20:00", "25:00", "30:00", "35:00", "40:00", "45:00", "50:00", "55:00" )

        suspend fun triggerAlarm() {
            kukuTextOnce()
            kukuSoundOnce()
        }

        if (currentTime in quarters) {
            triggerAlarm()
        }
    }


    // Hourly alarms
    @Suppress("unused", "unused")
    private suspend fun hourlyAlarms() {
        val getCurrentTime = Date()
        val formatter = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)

        for (i in 1..24) {
            val times = when {
                i < 13 -> i - 0
                else -> i - 12
            }

            val formattedHour = String.format("%02d", i)
            val hour = "${formattedHour}:00:00"

            if (hour == currentTime) {
                kukuMultipleTimes(times)
            }
        }
    }


    // Minutely alarms
    @Suppress("unused")
    private suspend fun minutelyAlarms() {
        val getCurrentTime = Date()
        val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)

        for (i in 0..59) {
            val times = when {
                i == 0 -> 10
                i < 11 -> i - 0
                i < 21 -> i - 10
                i < 31 -> i - 20
                i < 41 -> i - 30
                i < 51 -> i - 40
                else -> i - 50
            }

            val formattedMinute = String.format("%02d", i)
            val minute = "${formattedMinute}:00"

            if (minute == currentTime) {
                kukuMultipleTimes(times)
            }
        }
    }
}