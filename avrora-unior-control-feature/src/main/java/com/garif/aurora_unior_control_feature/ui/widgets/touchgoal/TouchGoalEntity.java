package com.garif.aurora_unior_control_feature.ui.widgets.touchgoal;

import com.garif.aurora_unior_control_feature.model.entities.widgets.PublisherLayerEntity;
import com.garif.aurora_unior_control_feature.model.repositories.message.Topic;

import geometry_msgs.PoseStamped;

public class TouchGoalEntity extends PublisherLayerEntity {

    public TouchGoalEntity() {
        this.topic = new Topic("/move_base_simple/goal", PoseStamped._TYPE);
        this.immediatePublish = true;
    }
}
