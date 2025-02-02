package com.garif.robot_selection_feature.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.garif.robot_selection_feature.domain.RobotSelection

class RobotSelectionDiffItemCallback : DiffUtil.ItemCallback<RobotSelection>() {

    override fun areItemsTheSame(oldItem: RobotSelection, newItem: RobotSelection): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: RobotSelection, newItem: RobotSelection): Boolean = oldItem == newItem
}
