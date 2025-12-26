package com.garif.core;

import geometry_msgs.Twist;

public interface Movable {
    void setMovement(Twist twist);
}
