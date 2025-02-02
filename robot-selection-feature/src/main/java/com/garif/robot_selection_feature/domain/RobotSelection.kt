package com.garif.robot_selection_feature.domain

import androidx.annotation.DrawableRes

data class RobotSelection(
    val name: String,
    @DrawableRes
    val drawableRes: Int,
)
