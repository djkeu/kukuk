package nl.djkeu.kukuk

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        ToDo: fix UI freeze in minutelyAlarms()
        ToDo: fix UI freezes in hourlyAlarms()
         */

        // Start and stop the alarms
        val kukuScope = MainScope()
        var job: Job? = null

        suspend fun loopAlarms() {
            while (true) {
                delay(1000)  // Needed to start the UI
                // quarterlyAlarms()
                // hourlyAlarms()
                minutelyAlarms()
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
        // ToDo: Switch to SuperToast for more options
        // ToDo: Get text from strings.xml
        // URL: https://github.com/JohnPersano/SuperToasts

        val kukuToast = Toast.makeText(applicationContext, "Kukuk!", Toast.LENGTH_SHORT)
        kukuToast.show()

        /*
        // ToDo: Remove later
        // Set kuku text
        val resultTextView: TextView = findViewById(R.id.textView2)
        resultTextView.text = getString(R.string.kukukTextView)

        // Reset kuku text
        Handler(Looper.getMainLooper()).postDelayed(
            { resultTextView.text = "" },
            1000
        )
        */
    }


    // Play kuku sound once
    private fun kukuSoundOnce() {
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
    private fun kukuSoundTimes(times: Int) {
        for (i in 1..times) {
            kukuSoundOnce()
            Thread.sleep(1000)
        }
    }


    // Quarterly alarms
    @Suppress("unused")
    private fun quarterlyAlarms() {
        val getCurrentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)

        val quarters = arrayOf( "15:00", "30:00", "45:00" )

        if (currentTime in quarters) {
            kukuTextOnce()
            kukuSoundOnce()
        }
    }


    // Hourly alarms
    @Suppress("unused", "unused")
    private fun hourlyAlarms() {
        val getCurrentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)

        for (i in 1..24) {
            fun checkHours(times: Int) {
                val formattedHour = String.format("%02d", i)
                val hour = "${formattedHour}:00:00"

                if (hour == currentTime) {
                    // Text once until SuperToasts are set correctly
                    kukuSoundTimes(times)
                    kukuTextOnce()
                    // kukuTextTimes(times)
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


    // Minutely alarms
    @Suppress("unused")
    private fun minutelyAlarms() {
        val getCurrentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        val currentTime = formatter.format(getCurrentTime)

        for (i in 0..59) {
            fun checkMinutes(times: Int) {
                val formattedMinute = String.format("%02d", i)
                val minute = "${formattedMinute}:00"

                if (minute == currentTime) {
                    // Text once until SuperToasts are set correctly
                    kukuSoundTimes(times)
                    kukuTextOnce()
                    // kukuTextTimes(times)
                }
            }

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
            checkMinutes(times)
        }
    }
}