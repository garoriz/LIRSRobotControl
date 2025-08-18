package com.garif.pmb2_control_feature.utils;

public class Constants {

    public static final double ROTATION_MAX = 2.0d;
    public static final double ROTATION_RATIO = 0.02d;
    public static final double COS30 = Math.cos(toRadians(30.0d));
    public static final double COS60 = Math.cos(toRadians(60.0d));
    public static final double PERCENTAGE = 100.0d;
    public static final String TOPIC_JOY_TELEOP = "/mobile_base_controller/cmd_vel";
    public enum Tags {
        EVENTS("AppEvents");
        private final String value;
        Tags(String value) { this.value = value; }
        public String getValue() { return value; }
    }
    public static double toRadians(double degrees) {
        return degrees / 180 * Math.PI;
    }
}
