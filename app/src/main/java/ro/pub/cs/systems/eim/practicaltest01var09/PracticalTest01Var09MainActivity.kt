package ro.pub.cs.systems.eim.practicaltest01var09

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var09MainActivity : AppCompatActivity() {

    private lateinit var nextTerm: EditText
    private lateinit var allTerm: EditText
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var09_main)

        nextTerm = findViewById(R.id.next_term)
        allTerm = findViewById(R.id.all_term)

        val addBut = findViewById<Button>(R.id.add)
        addBut.setOnClickListener {
            var res = ""
            if (nextTerm.text.toString().isNotEmpty()) {
                if (allTerm.text.toString().isEmpty()) {
                     allTerm.setText(allTerm.text.toString() + nextTerm.text.toString())
                } else {
                    allTerm.setText(allTerm.text.toString() + "+" + nextTerm.text.toString())
                }
            }
        }

        val activityResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val sum = result.data?.getIntExtra("resultSum", 0).toString()
                Toast.makeText(this, "The activity returned with result OK, sum:$sum", Toast.LENGTH_LONG)
                    .show()
            }
        }

        val compute = findViewById<Button>(R.id.compute)
        compute.setOnClickListener {
            if (allTerm.text.toString().isNotEmpty() ) {
                val intent = Intent(this, PracticalTest01Var09SecondaryActivity::class.java)
                Log.d("deb", allTerm.text.toString())
                intent.putExtra("INPUT1", allTerm.text.toString())
                activityResultsLauncher.launch(intent)
            }
        }
    }
}