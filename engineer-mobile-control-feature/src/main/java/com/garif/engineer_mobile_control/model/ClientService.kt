package com.garif.engineer_mobile_control.model

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import org.parceler.Parcels
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ClientService : Service() {

    lateinit var executor: ExecutorService

    override fun onCreate() {
        Log.d("Logs", "Service is created")
        super.onCreate()
        executor = Executors.newFixedThreadPool(1)
    }

    override fun onDestroy() {
        Log.d("Logs", "Service is destroyed")
        executor.shutdown()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("Logs", "Service \"onStartCommand\" is evoked")
        val client: Client = Parcels.unwrap(intent.getParcelableExtra("client"))
        executor.execute(client)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
