package com.garif.robot_selection_feature.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.garif.robot_selection_feature.domain.RobotSelection
import com.garif.robot_selection_feature.presentation.diffutil.RobotSelectionDiffItemCallback

class RobotSelectionListAdapter(private val action: (Int) -> Unit,) :
    ListAdapter<RobotSelection, RobotSelectionHolder>(RobotSelectionDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RobotSelectionHolder {
        return RobotSelectionHolder.create(parent, action)
    }

    override fun onBindViewHolder(holder: RobotSelectionHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<RobotSelection>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
