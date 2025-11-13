package ro.pub.cs.systems.eim.practicaltest01var09

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.os.Process
import androidx.annotation.RequiresApi
import java.util.Date
import java.util.Random

class ProcessingThread(private val context: Context, firstNumber: Int) :
    Thread() {
    private var isRunning = true
    private val random = Random()
    private val n = firstNumber

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun run() {
        Log.d(
            "Thread_Process",
            "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid()
        )
        while (isRunning) {
            sendMessage()
            sleep()
        }
        Log.d("Thread_Process", "Thread has stopped!")
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun sendMessage() {


        val intent = Intent()
        intent.setAction("ProcessingThread")
        intent.putExtra("INP1", n.toString())
        intent.putExtra("INP2",
            Date(System.currentTimeMillis()).toString())

        context.sendBroadcast(intent)
    }

    private fun sleep() {
        try {
            sleep(2000)
        } catch (interruptedException: InterruptedException) {
            interruptedException.printStackTrace()
        }
    }

    fun stopThread() {
        isRunning = false
    }
}
