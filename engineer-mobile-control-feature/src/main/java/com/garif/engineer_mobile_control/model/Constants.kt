package com.garif.engineer_mobile_control.model

import java.net.InetAddress

object Constants {

    /**
     * Speed
     */

    const val SPEED_STEP = 2730
    const val SPEED_DEFAULT: Short = 5460

    /**
     * Network
     */

    const val PORT_CLIENT: Short = 10001
    const val PORT_SERVER: Short = 10000

    private const val IP_SERVER = "10.42.0.1"

    const val REMOTE_CONTROL_PACKET_ID = 1
    const val REMOTE_CONTROL_PACKET_SIZE = 57
    const val FRAME_TYPE_POSITION = 0

    val ADDRESS_SERVER: InetAddress = InetAddress.getByName(IP_SERVER)

    lateinit var ADDRESS_CLIENT_DHCP: InetAddress
}
