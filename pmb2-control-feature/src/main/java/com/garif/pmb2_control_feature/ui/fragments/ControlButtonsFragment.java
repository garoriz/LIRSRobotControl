package com.garif.pmb2_control_feature.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.garif.pmb2_control_feature.R;
import com.garif.pmb2_control_feature.utils.Movable;

import geometry_msgs.Twist;

public class ControlButtonsFragment extends Fragment implements Movable {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_control_buttons, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setMovement(Twist twist) {

    }
}
