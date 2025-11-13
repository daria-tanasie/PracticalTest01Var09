package ro.pub.cs.systems.eim.practicaltest01var09

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var09SecondaryActivity : AppCompatActivity() {

    private lateinit var input1: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var09_secondary)

        val in1 = intent.getStringExtra("INPUT1")
        input1 = findViewById<TextView>(R.id.sum_show)

        val numbers = in1.toString().split("+")
        var sum = 0


        for (s in numbers) {
            sum += Integer.valueOf(s)
        }

        val ret = findViewById<Button>(R.id.ret)
        ret.setOnClickListener {
            val intent = Intent()
            intent.putExtra("resultSum",  sum)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}