package com.garif.robot_selection_feature.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.garif.robot_contreol_feature.databinding.ItemRobotSelectionBinding
import com.garif.robot_selection_feature.domain.RobotSelection

class RobotSelectionHolder(
    private val binding: ItemRobotSelectionBinding,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var robotSelection: RobotSelection? = null

    init {
        itemView.setOnClickListener {
            robotSelection?.navigationId?.also(action)
        }
    }

    fun bind(robotSelection: RobotSelection) {
        this.robotSelection = robotSelection
        with(binding) {
            ivConfigurationImage.load(robotSelection.drawableRes)
            tvConfigurationName.text = robotSelection.name
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (Int) -> Unit,
        ) = RobotSelectionHolder(
            ItemRobotSelectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}
