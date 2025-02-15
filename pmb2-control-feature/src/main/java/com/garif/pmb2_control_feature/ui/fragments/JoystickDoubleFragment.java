package com.garif.pmb2_control_feature.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.garif.pmb2_control_feature.R;
import com.garif.pmb2_control_feature.utils.Constants;
import com.garif.pmb2_control_feature.utils.Movable;

import geometry_msgs.Twist;
import io.github.controlwear.virtual.joystick.android.JoystickView;

public class JoystickDoubleFragment extends Fragment implements Movable {

    private double mvStrength, mvAngle, mvSign,
            rtStrength, rtAngle, rtSign,
            movementSpeed, rotationSpeed;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_joystick_double, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //JoystickView rtJoystick = view.findViewById(R.id.layout_joystick_left);
        //JoystickView mvJoystick = view.findViewById(R.id.layout_joystick_right);
        /*mvJoystick.setOnMoveListener((mvAngle, mvStrength) -> {
            Log.d(Constants.Tags.EVENTS.getValue(), "\"Movement\" Joystick's strength is: " + mvStrength);
            Log.d(Constants.Tags.EVENTS.getValue(), "\"Movement\" Joystick's angle is: " + mvAngle);
            this.mvStrength = mvStrength;
            this.mvAngle = mvAngle;
        });
        rtJoystick.setOnMoveListener((rtAngle, rtStrength) -> {
            Log.d(Constants.Tags.EVENTS.getValue(), "\"Rotation\" Joystick's strength is: " + rtStrength);
            Log.d(Constants.Tags.EVENTS.getValue(), "\"Rotation\" Joystick's angle is: " + rtAngle);
            this.rtStrength = rtStrength;
            this.rtAngle = rtAngle;
        });*/
    }

    @Override
    public void setMovement(Twist twist) {
        defineValuesEasyMode(twist);
    }

    private void defineValuesEasyMode(Twist twist) {
        twist.getLinear().setX(defineMovementValue());
        twist.getAngular().setZ(defineRotationValue());
    }

    private double defineMovementValue() {
        if (mvStrength == 0) return 0;
        movementSpeed = mvStrength / Constants.PERCENTAGE;
        if (Math.sin(Constants.toRadians(mvAngle)) < 0) mvSign = -1;
        else mvSign = 1;
        movementSpeed = mvSign * movementSpeed;
        Log.d(Constants.Tags.EVENTS.getValue(),
                "Movement speed is: " + movementSpeed);
        return movementSpeed;
    }

    private double defineRotationValue() {
        if (rtStrength == 0) return 0;
        rotationSpeed = rtStrength * Constants.ROTATION_RATIO;
        if (Math.cos(Constants.toRadians(rtAngle)) > 0) rtSign = -1;
        else rtSign = 1;
        rotationSpeed = rtSign * rotationSpeed;
        Log.d(Constants.Tags.EVENTS.getValue(),
                "Rotation speed is: " + rotationSpeed);
        return rotationSpeed;
    }
}
