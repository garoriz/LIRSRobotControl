package com.garif.aurora_unior_control_feature.nodes

import android.content.Context
import android.util.Log
import com.garif.aurora_unior_control_feature.Movable
import org.ros.namespace.GraphName
import org.ros.node.AbstractNodeMain
import org.ros.node.ConnectedNode
import org.ros.node.Node
import org.ros.node.topic.Publisher
import std_msgs.Float64
import java.util.Timer
import java.util.TimerTask

class VelocityNodeTeleop(private val topic: String, private val context: Context): AbstractNodeMain() {

    private val publisherTimer = Timer()
    private var movable: Movable? = null

    override fun getDefaultNodeName(): GraphName {
        return GraphName.of(context.getString(com.garif.core.R.string.joy_teleop))
    }

    override fun onStart(connectedNode: ConnectedNode?) {
        val publisher: Publisher<Float64>? =
            connectedNode?.newPublisher(topic, Float64._TYPE)
        val float64: Float64? = publisher?.newMessage()
        publisherTimer.schedule(object : TimerTask() {
            override fun run() {
                movable?.setVelocity(float64)
                publisher?.publish(float64)
                Log.d(
                    "Publisher in VelocityNodeTeleop",
                    float64.toString()
                )
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
