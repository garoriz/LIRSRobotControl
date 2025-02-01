package com.garif.robot_selection_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.garif.robot_contreol_feature.R

/**
 * A simple [Fragment] subclass.
 * Use the [RobotSelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RobotSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_robot_selection, container, false)
    }
}
