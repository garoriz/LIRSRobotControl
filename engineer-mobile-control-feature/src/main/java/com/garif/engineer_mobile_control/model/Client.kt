package com.garif.engineer_mobile_control.model

import android.util.Log
import org.parceler.Parcel
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket

@Parcel
class Client : Runnable {

    override fun run() {
        val packetClient =
            DatagramPacket(
                PackageRemoteControl.packet,
                PackageRemoteControl.packet.size,
                Constants.ADDRESS_SERVER,
                Constants.PORT_SERVER.toInt())
        val socketClient = DatagramSocket(null).apply {
            reuseAddress = true
            bind(
                java.net.InetSocketAddress(
                    Constants.ADDRESS_CLIENT_DHCP,
                    Constants.PORT_CLIENT.toInt()
                )
            )
        }
        try {
            while (true) {
                socketClient.send(packetClient)
                PackageManager.resetTorchPackage()
                Log.d("Logs", "Thread is running")
                Thread.sleep(200)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
