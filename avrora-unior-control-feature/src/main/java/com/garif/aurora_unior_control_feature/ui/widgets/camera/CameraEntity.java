package com.garif.aurora_unior_control_feature.ui.widgets.camera;

import com.garif.aurora_unior_control_feature.model.entities.widgets.SubscriberWidgetEntity;
import com.garif.aurora_unior_control_feature.model.repositories.message.Topic;

import sensor_msgs.CompressedImage;

public class CameraEntity extends SubscriberWidgetEntity {

    int colorScheme;
    boolean drawBehind;
    boolean useTimeStamp;


    public CameraEntity() {
        this.width = 8;
        this.height = 6;
        this.topic = new Topic("camera/depth/image_raw/compressed", CompressedImage._TYPE);
    }
}

