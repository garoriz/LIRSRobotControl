package com.garif.robot_selection_feature.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.garif.robot_contreol_feature.R
import com.garif.robot_contreol_feature.databinding.FragmentRobotSelectionBinding
import com.garif.robot_selection_feature.domain.RobotSelection
import com.garif.robot_selection_feature.presentation.adapter.RobotSelectionListAdapter

class RobotSelectionFragment : Fragment(R.layout.fragment_robot_selection) {

    private var binding: FragmentRobotSelectionBinding? = null
    private var robotSelectionListAdapter: RobotSelectionListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRobotSelectionBinding.bind(view)

        robotSelectionListAdapter = RobotSelectionListAdapter {
            view.findNavController().navigate(it)
        }

        binding?.robotSelectionList?.run {
            adapter = robotSelectionListAdapter
        }

        val robotSelectionList = mutableListOf(
            RobotSelection(
                "Servosila Engineer",
                R.drawable.img_config_engineer,
                R.id.action_robotSelectionFragment_to_engineerMobileControlActivity
            ),
            RobotSelection("TIAGo Base PMB2", R.drawable.img_config_pmb2, 0),
        )
        robotSelectionListAdapter?.submitList(robotSelectionList)
    }
}
