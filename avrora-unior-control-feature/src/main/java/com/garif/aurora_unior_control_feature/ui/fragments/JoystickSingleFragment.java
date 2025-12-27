package com.garif.aurora_unior_control_feature.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.garif.aurora_unior_control_feature.Movable;
import com.garif.core.Constants;
import com.garif.core.R;

import ackermann_msgs.AckermannDriveStamped;
import geometry_msgs.Twist;
import io.github.controlwear.virtual.joystick.android.JoystickView;

public class JoystickSingleFragment extends Fragment implements Movable {

    private double signRotation;
    private double strength;
    private double angle;
    private double movementSpeed;
    private double rotationSpeed;

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
    public void setMovement(AckermannDriveStamped ackermannDriveStamped) {
        defineValuesEasyMode(ackermannDriveStamped);
//        defineValuesHardMode(twist);
    }

    /**
     * Getting values without segmentation
     */

    private void defineValuesHardMode(Twist twist) {
        //twist.getLinear().setX(defineMovementValue(Constants.Companion.toRadians(angle)));
        //twist.getAngular().setZ(defineRotationValue(angle));
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
        if (Math.cos(Constants.Companion.toRadians(angle)) > 0) signRotation = -1.0d;
        else signRotation = 1.0d;
        double reverseDegree;
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

    private void defineValuesEasyMode(AckermannDriveStamped ackermannDriveStamped) {
        if (strength == 0) {
            movementSpeed = 0.0d;
            rotationSpeed = 0.0d;
        } else {
            double valCos = Math.cos(Constants.Companion.toRadians(angle));
            double valSin = Math.sin(Constants.Companion.toRadians(angle));
            double signMovement;
            if (valSin > 0) signMovement = 1.0d;
            else signMovement = -1.0d;
            if (valCos > 0) signRotation = -1.0d;
            else signRotation = 1.0d;
            if (valCos >= Constants.Companion.getCOS30() || valCos <= -1.0d * Constants.Companion.getCOS30()) {
                movementSpeed = 0.0d;
                rotationSpeed = signRotation * strength * Constants.ROTATION_RATIO;
            } else if (valCos >= Constants.Companion.getCOS60() || valCos <= -1.0d * Constants.Companion.getCOS60()) {
                movementSpeed = signMovement * strength / Constants.PERCENTAGE;
                if (signMovement > 0)
                    rotationSpeed = signRotation * strength * Constants.ROTATION_RATIO;
                else rotationSpeed = -1.0d * signRotation * strength * Constants.ROTATION_RATIO;
            } else {
                movementSpeed = signMovement * strength / Constants.PERCENTAGE;
                rotationSpeed = 0.0d;
            }
        }
        ackermannDriveStamped.getDrive().setSpeed((float) movementSpeed);
        Log.d(Constants.Tags.EVENTS.getValue(),
                "Movement speed is: " + movementSpeed);
        ackermannDriveStamped.getDrive().setSteeringAngle((float) rotationSpeed);
        Log.d(Constants.Tags.EVENTS.getValue(),
                "Rotation speed is: " + rotationSpeed);
    }
}
