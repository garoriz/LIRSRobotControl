package com.garif.aurora_unior_control_feature.nodes;

import com.garif.aurora_unior_control_feature.model.entities.widgets.BaseEntity;
import com.garif.aurora_unior_control_feature.model.entities.widgets.PublisherLayerEntity;
import com.garif.aurora_unior_control_feature.model.repositories.BaseData;

import org.ros.internal.message.Message;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import java.util.Timer;
import java.util.TimerTask;

public class PubNode extends AbstractNode {

    private Publisher<Message> publisher;
    private BaseData lastData;
    private Timer pubTimer;
    private long pubPeriod = 100L;
    private boolean immediatePublish = true;


    @Override
    public void onStart(ConnectedNode parentNode) {
        publisher = parentNode.newPublisher(topic.name, topic.type);

        this.createAndStartSchedule();
    }

    public void setData(BaseData data) {
        this.lastData = data;

        if (immediatePublish) {
            publish();
        }
    }

    public void setFrequency(float hz) {
        this.pubPeriod = (long) (1000 / hz);
    }

    public void setImmediatePublish(boolean flag) {
        this.immediatePublish = flag;
    }

    private void createAndStartSchedule() {
        if (pubTimer != null) {
            pubTimer.cancel();
        }

        if (immediatePublish) {
            return;
        }

        pubTimer = new Timer();
        pubTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                publish();
            }
        }, pubPeriod, pubPeriod);
    }

    private void publish() {
        if (publisher == null) {
            return;
        }
        if (lastData == null) {
            return;
        }

        Message message = lastData.toRosMessage(publisher, widget);
        publisher.publish(message);
    }

    @Override
    public void setWidget(BaseEntity widget) {
        super.setWidget(widget);

        if (!(widget instanceof PublisherLayerEntity)) {
            return;
        }

        PublisherLayerEntity pubEntity = (PublisherLayerEntity) widget;

        this.setImmediatePublish(pubEntity.immediatePublish);
        this.setFrequency(pubEntity.publishRate);
    }
}
