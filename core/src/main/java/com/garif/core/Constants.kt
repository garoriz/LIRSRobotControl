package com.garif.core

import kotlin.math.cos

class Constants {
    enum class Tags(val value: String) {
        EVENTS("AppEvents")
    }

    companion object {
        const val PERCENTAGE: Double = 100.0
        const val ROTATION_MAX: Double = 2.0
        val COS30: Double = cos(toRadians(30.0))
        const val ROTATION_RATIO: Double = 0.02
        val COS60: Double = cos(toRadians(60.0))

        fun toRadians(degrees: Double): Double {
            return degrees / 180 * Math.PI
        }
    }
}
