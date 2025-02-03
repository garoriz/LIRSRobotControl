package com.garif.engineer_mobile_control.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.garif.engineer_mobile_control.R
import com.garif.engineer_mobile_control.databinding.FragmentMovementBinding
import com.garif.engineer_mobile_control.model.PackageManager
import com.garif.engineer_mobile_control.ui.activities.EngineerMobileControlActivity.Companion.actionListener
import com.garif.engineer_mobile_control.ui.activities.EngineerMobileControlActivity.Companion.discloseListener

class MovementFragment : Fragment(R.layout.fragment_movement) {

    private var binding: FragmentMovementBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discloseListener.movement = view
        binding = FragmentMovementBinding.bind(view)
        binding?.btnStop?.setOnClickListener(actionListener)
        binding?.toggleMoveUp?.setOnCheckedChangeListener(actionListener)
        binding?.toggleMoveDown?.setOnCheckedChangeListener(actionListener)
        binding?.toggleMoveRight?.setOnCheckedChangeListener(actionListener)
        binding?.toggleMoveLeft?.setOnCheckedChangeListener(actionListener)
        binding?.toggleFlippers?.setOnCheckedChangeListener(discloseListener)
        binding?.toggleFlippersUp?.setOnCheckedChangeListener(actionListener)
        binding?.toggleFlippersDown?.setOnCheckedChangeListener(actionListener)
        binding?.toggleTorch?.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                PackageManager.turnOnTorch()
            else
                PackageManager.turnOffTorch()
        }
    }
}
