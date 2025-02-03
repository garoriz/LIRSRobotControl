package com.garif.engineer_mobile_control.utils.listeners

import android.widget.CompoundButton
import android.widget.ToggleButton
import com.garif.engineer_mobile_control.R
import com.garif.engineer_mobile_control.model.PackageManager

class OneActionCheckedChangeListener : OneToggleCheckedChangeListener() {

    override fun actionSwitchOn(toggle: CompoundButton) {
        when (toggle) {
            toggle.findViewById<ToggleButton>(R.id.toggle_move_up) ->
                PackageManager.startMove(false)

            toggle.findViewById<ToggleButton>(R.id.toggle_move_down) ->
                PackageManager.startMove(true)

            toggle.findViewById<ToggleButton>(R.id.toggle_move_right) ->
                PackageManager.startRotate(true)

            toggle.findViewById<ToggleButton>(R.id.toggle_move_left) ->
                PackageManager.startRotate(false)

            toggle.findViewById<ToggleButton>(R.id.toggle_waist_left) ->
                PackageManager.startWaist(false)

            toggle.findViewById<ToggleButton>(R.id.toggle_waist_right) ->
                PackageManager.startWaist(true)

            toggle.findViewById<ToggleButton>(R.id.toggle_shoulder_up) ->
                PackageManager.startShoulder(true)

            toggle.findViewById<ToggleButton>(R.id.toggle_shoulder_down) ->
                PackageManager.startShoulder(false)

            toggle.findViewById<ToggleButton>(R.id.toggle_elbow_up) ->
                PackageManager.startElbow(false)

            toggle.findViewById<ToggleButton>(R.id.toggle_elbow_down) ->
                PackageManager.startElbow(true)

            toggle.findViewById<ToggleButton>(R.id.toggle_neck_up) ->
                PackageManager.startNeck(false)

            toggle.findViewById<ToggleButton>(R.id.toggle_neck_down) ->
                PackageManager.startNeck(true)

            toggle.findViewById<ToggleButton>(R.id.toggle_gripper_free) ->
                PackageManager.startOpenGripper()

            toggle.findViewById<ToggleButton>(R.id.toggle_gripper_grab) ->
                PackageManager.startCloseGripper()

            toggle.findViewById<ToggleButton>(R.id.toggle_flippers_up) ->
                PackageManager.startFlippersUp()

            toggle.findViewById<ToggleButton>(R.id.toggle_flippers_down) ->
                PackageManager.startFlippersDown()
        }
    }

    override fun actionSwitchOff(toggle: CompoundButton) {
        when (toggle) {
            toggle.findViewById<ToggleButton>(R.id.toggle_move_up),
            toggle.findViewById<ToggleButton>(
                R.id.toggle_move_down
            ),
            -> PackageManager.stopMove()

            toggle.findViewById<ToggleButton>(R.id.toggle_move_left),
            toggle.findViewById<ToggleButton>(
                R.id.toggle_move_right
            ),
            -> PackageManager.stopRotate()

            toggle.findViewById<ToggleButton>(R.id.toggle_waist_left),
            toggle.findViewById<ToggleButton>(
                R.id.toggle_waist_right
            ),
            -> PackageManager.stopWaist()

            toggle.findViewById<ToggleButton>(R.id.toggle_shoulder_up),
            toggle.findViewById<ToggleButton>(
                R.id.toggle_shoulder_down
            ),
            -> PackageManager.stopShoulder()

            toggle.findViewById<ToggleButton>(R.id.toggle_elbow_up),
            toggle.findViewById<ToggleButton>(
                R.id.toggle_elbow_down
            ),
            -> PackageManager.stopElbow()

            toggle.findViewById<ToggleButton>(R.id.toggle_neck_up),
            toggle.findViewById<ToggleButton>(
                R.id.toggle_neck_down
            ),
            -> PackageManager.stopNeck()

            toggle.findViewById<ToggleButton>(R.id.toggle_gripper_free) ->
                PackageManager.stopOpenGripper()
            toggle.findViewById<ToggleButton>(R.id.toggle_gripper_grab) ->
                PackageManager.stopCloseGripper()
            toggle.findViewById<ToggleButton>(R.id.toggle_flippers_up) ->
                PackageManager.stopFlippersUp()
            toggle.findViewById<ToggleButton>(R.id.toggle_flippers_down) ->
                PackageManager.stopFlippersDown()
        }
    }
}
