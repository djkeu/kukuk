package nl.djkeu.kukuk

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val kukuButton: Button = findViewById(R.id.button)
        kukuButton.setOnClickListener {
            playShowKuku()
        }
        runAlarms()


    }

        private fun playShowKuku() {
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


        private fun minutelyAlarms() {
            val getCurrentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("mm", Locale.getDefault())
            val currentTime = formatter.format(getCurrentTime)

            // val quarters = arrayOf("00", "15", "30", "45")
            val quarters = arrayOf("00", "10", "05", "15", "20", "25", "30", "35", "40", "45", "50", "55")

            if (currentTime in quarters) {
                playShowKuku()
                }
            }


        private fun runAlarms() {
            minutelyAlarms()
            }
}

