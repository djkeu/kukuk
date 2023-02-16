package nl.djkeu.kukuk

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
    private var job: Job? = null

    private suspend fun delayWithMillis(millis: Long) = withContext(Dispatchers.Default) {
        delay(millis)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start and stop the alarms
        val kukuScope = MainScope()

        suspend fun startSelectedAlarms() {
            // quarterlyAlarms()
            // hourlyAlarms()
            minutelyAlarms()
        }

        suspend fun loopAlarms() {
            while (true) {
                delay(1000)  // Needed to start the UI
                startSelectedAlarms()
            }
        }

        fun startAlarms() {
            job = kukuScope.launch {
            loopAlarms()
            }
        }

        fun stopAlarms() {
            job?.cancel()
            job = null
        }

        runBlocking {
            stopAlarms()
            startAlarms()
        }
    }


    // Show kuku text once
    private fun kukuTextOnce() {
        /*
        val kukuToast = Toast.makeText(applicationContext,
            getString((R.string.kukukTextView)),
            Toast.LENGTH_LONG)

        kukuToast.show()
        */

        // ToDo: UI freezing issues
        // Instead, use data binding or ViewBinding to bind views to Kotlin classes.

        // Set kuku text
        val resultTextView: TextView = findViewById(R.id.textView2)
        resultTextView.text = getString(R.string.kukukTextView)

        // Reset kuku text
        Handler(Looper.getMainLooper()).postDelayed(
            { resultTextView.text = "" },
            1000
        )
    }


    // Play kuku sound once
    private fun kukuSoundOnce() {
        // TODO: Add try-catch block around MediaPlayer.create call, catch exceptions
        val resourceId = resources.getIdentifier("keukuk", "raw", packageName)
        val kukuPlayer = MediaPlayer.create(this, resourceId)
        kukuPlayer.start()
    }


    // Show kuku text multiple times
    @Suppress("unused")
    private fun kukuTextTimes(times: Int) {
        for (i in 1..times) {
            kukuTextOnce()
        }
    }


    // Play kuku sound multiple times
    private suspend fun kukuSoundTimes(times: Int) {
        for (i in 1..times) {
            kukuSoundOnce()
            delayWithMillis(1000)
        }
    }


    // Quarterly alarms
    @Suppress("unused")
    private fun quarterlyAlarms() {
        // val getCurrentTime = Calendar.getInstance().time
        val getCurrentTime = Date()
        // object formatter is moved to a class level variable, to be used in all alarms
        // val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)

        val quarters = arrayOf( "15:00", "30:00", "45:00" )

        if (currentTime in quarters) {
            kukuTextOnce()
            kukuSoundOnce()
        }
    }


    // Hourly alarms
    @Suppress("unused", "unused")
    private suspend fun hourlyAlarms() {
        val getCurrentTime = Date()
        val currentTime = formatter.format(getCurrentTime)

        // TODO: Use when statement instead of long if..else ladder
        for (i in 1..24) {
            val times = if (i < 13) {
                i - 0
            } else {
                i - 12
            }

            val formattedHour = String.format("%02d", i)
            val hour = "${formattedHour}:00:00"

            if (hour == currentTime) {
                // Text once until SuperToasts are set correctly
                kukuSoundTimes(times)
                kukuTextOnce()
                // kukuTextTimes(times)
            }
        }
    }


    // Minutely alarms
    @Suppress("unused")
    private suspend fun minutelyAlarms() {
        while (true) {
            val getCurrentTime = Date()
            val currentTime = formatter.format(getCurrentTime)

            // TODO: Use when statement instead of long if..else ladder
            for (i in 0..59) {
                val times = if (i == 0) {
                    10
                } else if (i < 11) {
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

                val formattedMinute = String.format("%02d", i)
                val minute = "${formattedMinute}:00"

                if (minute == currentTime) {
                    kukuSoundTimes(times)
                    kukuTextOnce()
                }
            }
            delay(1000)
        }
    }
}