package com.garif.pmb2_control_feature.nodes;

import android.content.Context;

import com.garif.pmb2_control_feature.Movable;

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
    private final Context context;

    public NodeTeleop(String topic, Context context) {
        this.topic = topic;
        publisherTimer = new Timer();
        this.context = context;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of(context.getString(com.garif.core.R.string.joy_teleop));
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
