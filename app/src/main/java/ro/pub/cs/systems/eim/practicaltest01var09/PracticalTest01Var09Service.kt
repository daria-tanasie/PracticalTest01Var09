package ro.pub.cs.systems.eim.practicaltest01var09

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.util.Objects


class PracticalTest01Var09Service : Service() {
    var processingThread: ProcessingThread? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val CHANNEL_ID = "my_channel_01"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("")
            .setContentText("")
            .build()


        startForeground(1, notification)
        Log.d("service", "service started")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val firstNumber = intent?.getIntExtra("F1", -1) ?:0
        processingThread = ProcessingThread(this, firstNumber)
        processingThread!!.start()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    override fun onDestroy() {
        processingThread!!.stopThread()
        super.onDestroy()
    }
}