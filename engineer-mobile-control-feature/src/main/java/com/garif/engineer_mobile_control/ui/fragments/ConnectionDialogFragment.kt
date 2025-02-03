package com.garif.engineer_mobile_control.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.DialogFragment
import com.garif.engineer_mobile_control.R
import com.garif.engineer_mobile_control.databinding.FragmentConnectionBinding

class ConnectionDialogFragment : DialogFragment(R.layout.fragment_connection) {

    private var binding: FragmentConnectionBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConnectionBinding.bind(view)
        binding?.btnConnect?.setOnClickListener {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            resources.getDimensionPixelSize(R.dimen.dialog_connection_width),
            resources.getDimensionPixelSize(R.dimen.dialog_connection_height)
        )
        dialog?.window?.setBackgroundDrawableResource(R.color.color_black_translucent)
    }
}
