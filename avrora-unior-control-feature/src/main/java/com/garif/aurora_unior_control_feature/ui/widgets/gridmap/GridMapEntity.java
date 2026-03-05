package com.garif.aurora_unior_control_feature.ui.widgets.gridmap;

import com.garif.aurora_unior_control_feature.model.entities.widgets.SubscriberLayerEntity;
import com.garif.aurora_unior_control_feature.model.repositories.message.Topic;

import nav_msgs.OccupancyGrid;

public class GridMapEntity extends SubscriberLayerEntity {
    
    public GridMapEntity() {
        this.topic = new Topic("/map", OccupancyGrid._TYPE);
    }
    
}
