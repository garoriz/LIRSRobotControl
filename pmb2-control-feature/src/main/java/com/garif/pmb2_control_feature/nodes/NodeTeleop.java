package com.garif.pmb2_control_feature.nodes;

import com.garif.pmb2_control_feature.utils.Movable;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.topic.Publisher;

import java.util.Timer;
import java.util.TimerTask;

import geometry_msgs.Twist;

public class NodeTeleop extends AbstractNodeMain  {

    private Movable movable;
    private final Timer publisherTimer;
    private final String topic;

    public NodeTeleop(String topic) {
        this.topic = topic;
        publisherTimer = new Timer();
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("joy_teleop");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        Publisher<Twist> publisher =
                connectedNode.newPublisher(topic, Twist._TYPE);
        Twist twist = publisher.newMessage();
        publisherTimer.schedule(new TimerTask() {

            public void run() {
                publisher.publish(twist);
                movable.setMovement(twist);
            }
        }, 0L, 80L);
    }

    @Override
    public void onShutdown(Node node) {}

    @Override
    public void onShutdownComplete(Node node) {
        publisherTimer.cancel();
        publisherTimer.purge();
    }

    @Override
    public void onError(Node node, Throwable throwable) {}

    public void setMovable(Movable movable) {
        this.movable = movable;
    }
}
