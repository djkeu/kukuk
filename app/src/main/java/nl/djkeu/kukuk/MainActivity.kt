package nl.djkeu.kukuk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val kukuButton: Button = findViewById(R.id.button)
        kukuButton.setOnClickListener {
            val kukuToast = Toast.makeText(this, "Kukuk!", Toast.LENGTH_SHORT)
            kukuToast.show()
        }
    }
}