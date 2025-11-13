package ro.pub.cs.systems.eim.practicaltest01var09

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var09MainActivity : AppCompatActivity() {

    private lateinit var nextTerm: EditText
    private lateinit var allTerm: EditText
    var sum = 0
    private val intentFilter = IntentFilter()

    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val n1 = intent.getIntExtra("INP1", 0)
                val n2 = intent.getStringExtra("INP2")
                Toast.makeText(context, "sum:$n1, date-time$n2", Toast.LENGTH_LONG);
                Log.d("brc", "$n1 $n2")
            }
        }
    }

    
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

        var prevSum = ""

        val activityResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val r = result.data?.getIntExtra("resultSum", 0).toString()
                sum = Integer.valueOf(r)
                Toast.makeText(this, "The activity returned with result OK, sum:$r", Toast.LENGTH_LONG)
                    .show()
            }
        }

        val compute = findViewById<Button>(R.id.compute)
        compute.setOnClickListener {
            if (allTerm.text.toString().isNotEmpty() && prevSum != allTerm.text.toString()) {
                val intent = Intent(this, PracticalTest01Var09SecondaryActivity::class.java)
                Log.d("deb", allTerm.text.toString())
                intent.putExtra("INPUT1", allTerm.text.toString())
                prevSum = allTerm.text.toString()
                activityResultsLauncher.launch(intent)
            } else if (prevSum == allTerm.text.toString()) {
                Toast.makeText(this, "The activity returned with result OK, sum:$sum", Toast.LENGTH_LONG)
                    .show()
            }

            if (sum > 10) {
                val serviceIntent = Intent(this, PracticalTest01Var09Service::class.java)
                serviceIntent.putExtra("F1", sum)
                applicationContext.startService(serviceIntent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("I1", nextTerm.text.toString())
        outState.putString("I2", allTerm.text.toString())
        outState.putInt("I3", sum)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nextTerm.setText(savedInstanceState.getString("I1"))
        allTerm.setText(savedInstanceState.getString("I2"))
        sum = savedInstanceState.getInt("I3")
        Log.d("sum", sum.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        registerReceiver(messageBroadcastReceiver, IntentFilter("ProcessingThread"), Context.RECEIVER_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(messageBroadcastReceiver)
    }

    override fun onDestroy() {
        val intent = Intent(applicationContext, PracticalTest01Var09Service::class.java)
        applicationContext.stopService(intent)
        super.onDestroy()
    }
}