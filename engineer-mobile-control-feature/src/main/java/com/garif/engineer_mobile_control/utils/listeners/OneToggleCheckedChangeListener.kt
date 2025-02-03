package com.garif.engineer_mobile_control.utils.listeners

import android.view.View
import android.widget.CompoundButton
import android.widget.ToggleButton
import com.garif.engineer_mobile_control.model.PackageManager
import com.garif.engineer_mobile_control.ui.activities.EngineerMobileControlActivity

abstract class OneToggleCheckedChangeListener : CompoundButton.OnCheckedChangeListener,
    View.OnClickListener {

    private var togglePrevious: CompoundButton = ToggleButton(EngineerMobileControlActivity.context)

    override fun onCheckedChanged(toggleCurrent: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            if (togglePrevious != toggleCurrent && togglePrevious.isChecked) {
                togglePrevious.isChecked = false
                actionSwitchOff(togglePrevious)
            }
            actionSwitchOn(toggleCurrent)
            togglePrevious = toggleCurrent
        } else {
            actionSwitchOff(toggleCurrent)
        }
    }

    abstract fun actionSwitchOn(toggle: CompoundButton)

    abstract fun actionSwitchOff(toggle: CompoundButton)

    override fun onClick(view: View) {
        togglePrevious.isChecked = false
        PackageManager.stopAll()
    }
}
