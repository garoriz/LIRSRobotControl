package com.garif.engineer_mobile_control.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.garif.engineer_mobile_control.R
import com.garif.engineer_mobile_control.databinding.FragmentJointsBinding
import com.garif.engineer_mobile_control.ui.activities.EngineerMobileControlActivity.Companion.actionListener
import com.garif.engineer_mobile_control.ui.activities.EngineerMobileControlActivity.Companion.discloseListener

class JointsFragment : Fragment(R.layout.fragment_joints) {

    private var binding: FragmentJointsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discloseListener.joints = view
        binding = FragmentJointsBinding.bind(view)
        binding?.btnStop?.setOnClickListener(actionListener)
        binding?.toggleWaist?.setOnCheckedChangeListener(discloseListener)
        binding?.toggleWaistLeft?.setOnCheckedChangeListener(actionListener)
        binding?.toggleWaistRight?.setOnCheckedChangeListener(actionListener)
        binding?.toggleShoulder?.setOnCheckedChangeListener(discloseListener)
        binding?.toggleShoulderUp?.setOnCheckedChangeListener(actionListener)
        binding?.toggleShoulderDown?.setOnCheckedChangeListener(actionListener)
        binding?.toggleElbow?.setOnCheckedChangeListener(discloseListener)
        binding?.toggleElbowUp?.setOnCheckedChangeListener(actionListener)
        binding?.toggleElbowDown?.setOnCheckedChangeListener(actionListener)
        binding?.toggleNeck?.setOnCheckedChangeListener(discloseListener)
        binding?.toggleNeckUp?.setOnCheckedChangeListener(actionListener)
        binding?.toggleNeckDown?.setOnCheckedChangeListener(actionListener)
        binding?.toggleGripper?.setOnCheckedChangeListener(discloseListener)
        binding?.toggleGripperFree?.setOnCheckedChangeListener(actionListener)
        binding?.toggleGripperGrab?.setOnCheckedChangeListener(actionListener)
    }
}
