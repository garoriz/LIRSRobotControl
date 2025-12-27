package com.garif.aurora_unior_control_feature

import ackermann_msgs.AckermannDriveStamped

interface Movable {
    fun setMovement(ackermannDriveStamped: AckermannDriveStamped?)
}
