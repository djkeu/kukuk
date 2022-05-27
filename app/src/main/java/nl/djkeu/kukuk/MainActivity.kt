package nl.djkeu.kukuk

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun showKuku() {
            val resultTextView: TextView = findViewById(R.id.textView2)
            resultTextView.text = getString(R.string.kukukTextView)

            Handler(Looper.getMainLooper()).postDelayed(
                { resultTextView.text = "" },
                1000
            )
        }

        fun playKukuOnce() {
            val resId = getResources().getIdentifier("keukuk", "raw", getPackageName())
            val mediaPlayer = MediaPlayer.create(this, resId)
            mediaPlayer.start()
        }

        val kukuButton: Button = findViewById(R.id.button)
        kukuButton.setOnClickListener {
            playKukuOnce()
            showKuku()
        }

        fun minutelyCall() {
            val getCurrentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("ss.SS")
            val currentTime = getCurrentTime.format(formatter)

            if (currentTime == "01.01") {
                playKukuOnce()
            }
        }
        minutelyCall()
    }
}