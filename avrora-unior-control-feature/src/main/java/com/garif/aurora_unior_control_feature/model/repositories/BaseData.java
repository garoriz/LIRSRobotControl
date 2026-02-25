package com.garif.aurora_unior_control_feature.model.repositories;

import com.garif.aurora_unior_control_feature.model.entities.BaseEntity;
import com.garif.aurora_unior_control_feature.model.repositories.message.Topic;

import org.ros.internal.message.Message;
import org.ros.node.topic.Publisher;

public abstract class BaseData {

    protected Topic topic;


    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return this.topic;
    }

    public Message toRosMessage(Publisher<Message> publisher, BaseEntity widget) {
        return null;
    }
}
