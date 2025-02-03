package com.garif.engineer_mobile_control.utils.listeners

import android.view.View
import android.widget.CompoundButton
import android.widget.ToggleButton
import com.garif.engineer_mobile_control.R

class OneDiscloseCheckedChangeListener : OneToggleCheckedChangeListener() {

    lateinit var joints: View
    lateinit var movement: View

    override fun actionSwitchOn(toggle: CompoundButton) {
        when (toggle) {
            toggle.findViewById<ToggleButton>(R.id.toggle_waist) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_waist_left).visibility = View.VISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_waist_right).visibility = View.VISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_shoulder) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_shoulder_up).visibility = View.VISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_shoulder_down).visibility =
                    View.VISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_elbow) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_elbow_up).visibility = View.VISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_elbow_down).visibility = View.VISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_neck) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_neck_up).visibility = View.VISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_neck_down).visibility = View.VISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_gripper) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_gripper_free).visibility =
                    View.VISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_gripper_grab).visibility =
                    View.VISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_flippers) -> {
                movement.findViewById<ToggleButton>(R.id.toggle_flippers_up).visibility =
                    View.VISIBLE
                movement.findViewById<ToggleButton>(R.id.toggle_flippers_down).visibility =
                    View.VISIBLE
            }
        }
    }

    override fun actionSwitchOff(toggle: CompoundButton) {
        when (toggle) {
            toggle.findViewById<ToggleButton>(R.id.toggle_waist) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_waist_left).visibility =
                    View.INVISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_waist_right).visibility =
                    View.INVISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_shoulder) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_shoulder_up).visibility =
                    View.INVISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_shoulder_down).visibility =
                    View.INVISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_elbow) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_elbow_up).visibility = View.INVISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_elbow_down).visibility =
                    View.INVISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_neck) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_neck_up).visibility = View.INVISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_neck_down).visibility = View.INVISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_gripper) -> {
                joints.findViewById<ToggleButton>(R.id.toggle_gripper_free).visibility =
                    View.INVISIBLE
                joints.findViewById<ToggleButton>(R.id.toggle_gripper_grab).visibility =
                    View.INVISIBLE
            }

            toggle.findViewById<ToggleButton>(R.id.toggle_flippers) -> {
                movement.findViewById<ToggleButton>(R.id.toggle_flippers_up).visibility =
                    View.INVISIBLE
                movement.findViewById<ToggleButton>(R.id.toggle_flippers_down).visibility =
                    View.INVISIBLE
            }
        }
    }
}
