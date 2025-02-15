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

public class JoystickSingleFragment extends Fragment implements Movable {

    private double signMovement, signRotation, strength, angle, valSin, valCos,
            reverseDegree, movementSpeed, rotationSpeed;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_joystick_single, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JoystickView joystick = view.findViewById(R.id.virtual_joystick_layout);
        joystick.setOnMoveListener((angle, strength) -> {
            Log.d(Constants.Tags.EVENTS.getValue(), "Strength is: " + strength);
            Log.d(Constants.Tags.EVENTS.getValue(), "Angle is: " + angle);
            this.strength = strength;
            this.angle = angle;
        });
    }

    @Override
    public void setMovement(Twist twist) {
        defineValuesEasyMode(twist);
//        defineValuesHardMode(twist);
    }

    /**
     * Getting values without segmentation
     */

    private void defineValuesHardMode(Twist twist) {
        twist.getLinear().setX(defineMovementValue(Constants.toRadians(angle)));
        twist.getAngular().setZ(defineRotationValue(angle));
    }

    private double defineMovementValue(double angleRadians) {
        if (Math.sin(angleRadians) == 0) return 0;
        movementSpeed = strength / Constants.PERCENTAGE;
        if (Math.sin(angleRadians) < 0)
            movementSpeed *= -1;
        Log.d(Constants.Tags.EVENTS.getValue(),
                "Movement speed is: " + movementSpeed);
        return movementSpeed;
    }

    private double defineRotationValue(double angle) {
        if (strength == 0) return 0;
        if (Math.cos(Constants.toRadians(angle)) > 0) signRotation = -1.0d;
        else signRotation = 1.0d;
        if ((angle >= 0.0d && angle < 180.0d)) reverseDegree = 90.0d;
        else reverseDegree = 270.0d;
        rotationSpeed = Math.abs(angle - reverseDegree) / 90.0d * Constants.ROTATION_MAX * signRotation;
        Log.d(Constants.Tags.EVENTS.getValue(),
                "Rotation speed is: " + rotationSpeed);
        return rotationSpeed;
    }

    /**
     * Getting values with segmentation
     */

    private void defineValuesEasyMode(Twist twist) {
        if (strength == 0) {
            movementSpeed = 0.0d;
            rotationSpeed = 0.0d;
        } else {
            valCos = Math.cos(Constants.toRadians(angle));
            valSin = Math.sin(Constants.toRadians(angle));
            if (valSin > 0) signMovement = 1.0d;
            else signMovement = -1.0d;
            if (valCos > 0) signRotation = -1.0d;
            else signRotation = 1.0d;
            if (valCos >= Constants.COS30 || valCos <= -1.0d * Constants.COS30) {
                movementSpeed = 0.0d;
                rotationSpeed = signRotation * strength * Constants.ROTATION_RATIO;
            } else if (valCos >= Constants.COS60 || valCos <= -1.0d * Constants.COS60) {
                movementSpeed = signMovement * strength / Constants.PERCENTAGE;
                if(signMovement > 0) rotationSpeed = signRotation * strength * Constants.ROTATION_RATIO;
                else rotationSpeed = -1.0d * signRotation * strength * Constants.ROTATION_RATIO;
            } else {
                movementSpeed = signMovement * strength / Constants.PERCENTAGE;
                rotationSpeed = 0.0d;
            }
        }
        twist.getLinear().setX(movementSpeed);
        Log.d(Constants.Tags.EVENTS.getValue(),
                "Movement speed is: " + movementSpeed);
        twist.getAngular().setZ(rotationSpeed);
        Log.d(Constants.Tags.EVENTS.getValue(),
                "Rotation speed is: " + rotationSpeed);
    }
}
