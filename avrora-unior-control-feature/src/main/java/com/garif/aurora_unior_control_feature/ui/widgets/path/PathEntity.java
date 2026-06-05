package com.garif.aurora_unior_control_feature.ui.widgets.path;

import com.garif.aurora_unior_control_feature.model.entities.widgets.SubscriberLayerEntity;
import com.garif.aurora_unior_control_feature.model.repositories.message.Topic;

public class PathEntity extends SubscriberLayerEntity {

    public float lineWidth;
    public String lineColor;


    public PathEntity() {
        this.topic = new Topic("/move_base/GlobalPlanner/plan", nav_msgs.Path._TYPE);
        this.lineWidth = 4;
        this.lineColor = "ff0000ff";
    }
}
