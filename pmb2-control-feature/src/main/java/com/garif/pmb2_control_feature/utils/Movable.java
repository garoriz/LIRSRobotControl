package com.garif.pmb2_control_feature.utils;

import geometry_msgs.Twist;

public interface Movable {
    void setMovement(Twist twist);
}
