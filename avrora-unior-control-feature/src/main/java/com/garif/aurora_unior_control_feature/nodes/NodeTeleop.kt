package com.garif.aurora_unior_control_feature.nodes

import android.content.Context
import com.garif.core.Movable
import geometry_msgs.Twist
import org.ros.namespace.GraphName
import org.ros.node.AbstractNodeMain
import org.ros.node.ConnectedNode
import org.ros.node.Node
import org.ros.node.topic.Publisher
import java.util.Timer
import java.util.TimerTask

class NodeTeleop(private val topic: String, private val context: Context): AbstractNodeMain() {

    private val publisherTimer = Timer()
    private var movable: Movable? = null

    override fun getDefaultNodeName(): GraphName {
        return GraphName.of(context.getString(com.garif.core.R.string.joy_teleop))
    }

    override fun onStart(connectedNode: ConnectedNode?) {
        val publisher: Publisher<Twist>? =
            connectedNode?.newPublisher(topic, Twist._TYPE)
        val twist: Twist? = publisher?.newMessage()
        publisherTimer.schedule(object : TimerTask() {
            override fun run() {
                publisher?.publish(twist)
                movable?.setMovement(twist)
            }
        }, 0L, 80L)
    }

    override fun onShutdownComplete(node: Node?) {
        publisherTimer.cancel()
        publisherTimer.purge()
    }

    fun setMovable(movable: Movable) {
        this.movable = movable
    }
}
