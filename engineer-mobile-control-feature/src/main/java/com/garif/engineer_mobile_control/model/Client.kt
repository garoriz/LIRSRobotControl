package com.garif.engineer_mobile_control.model

import android.util.Log
import org.parceler.Parcel
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket

@Parcel
class Client : Runnable {

    override fun run() {
        val PACKET_CLIENT =
            DatagramPacket(
                PackageRemoteControl.packet,
                PackageRemoteControl.packet.size,
                Constants.ADDRESS_SERVER,
                Constants.PORT_SERVER.toInt())
        val SOCKET_CLIENT =
            DatagramSocket(
                Constants.PORT_CLIENT.toInt(),
                Constants.ADDRESS_CLIENT_DHCP)
        try {
            while (true) {
                SOCKET_CLIENT.send(PACKET_CLIENT)
                PackageManager.resetTorchPackage()
                Log.d("Rizvan", PACKET_CLIENT.data[34].toString())
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
