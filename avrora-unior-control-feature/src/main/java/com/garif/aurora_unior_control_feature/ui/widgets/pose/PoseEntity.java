package com.garif.aurora_unior_control_feature.ui.widgets.pose;

import com.garif.aurora_unior_control_feature.model.entities.widgets.SubscriberLayerEntity;
import com.garif.aurora_unior_control_feature.model.repositories.message.Topic;

import geometry_msgs.PoseWithCovarianceStamped;

public class PoseEntity extends SubscriberLayerEntity {


    public PoseEntity() {
        this.topic = new Topic("/amcl_pose", PoseWithCovarianceStamped._TYPE);
    }
}
