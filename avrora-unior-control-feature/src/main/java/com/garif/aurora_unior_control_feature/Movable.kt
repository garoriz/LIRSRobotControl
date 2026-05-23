package com.garif.aurora_unior_control_feature

import std_msgs.Float64

interface Movable {
    fun setSteering(steering: Float64?)
    fun setVelocity(velocity: Float64?)
}
