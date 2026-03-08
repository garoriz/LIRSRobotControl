package com.garif.aurora_unior_control_feature.ui.activities

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import com.garif.aurora_unior_control_feature.Constants
import com.garif.aurora_unior_control_feature.Movable
import com.garif.aurora_unior_control_feature.R
import com.garif.aurora_unior_control_feature.model.repositories.TransformProvider
import com.garif.aurora_unior_control_feature.model.repositories.message.RosData
import com.garif.aurora_unior_control_feature.nodes.NodeTeleop
import com.garif.aurora_unior_control_feature.nodes.SubNode
import com.garif.aurora_unior_control_feature.ui.fragments.JoystickDoubleFragment
import com.garif.aurora_unior_control_feature.ui.fragments.JoystickSingleFragment
import com.garif.aurora_unior_control_feature.ui.widgets.camera.CameraEntity
import com.garif.aurora_unior_control_feature.ui.widgets.camera.CameraView
import com.garif.aurora_unior_control_feature.ui.widgets.gridmap.GridMapEntity
import com.garif.aurora_unior_control_feature.ui.widgets.gridmap.GridMapView
import com.garif.aurora_unior_control_feature.ui.widgets.pose.PoseEntity
import com.garif.aurora_unior_control_feature.ui.widgets.pose.PoseView
import com.garif.aurora_unior_control_feature.ui.widgets.viz2d.Viz2DEntity
import com.garif.aurora_unior_control_feature.ui.widgets.viz2d.Viz2DView
import com.github.rosjava.android_remocons.common_tools.apps.RosAppActivity
import org.ros.address.InetAddressFactory
import org.ros.node.NodeConfiguration
import org.ros.node.NodeMainExecutor
import org.ros.time.NtpTimeProvider
import org.ros.time.TimeProvider
import org.ros.time.WallTimeProvider
import tf2_msgs.TFMessage
import java.util.concurrent.TimeUnit


private const val TAG: String = "MapNav"

class AvroraUniorControlActivity : RosAppActivity("AvroraUniorMobile", "AvroraUniorMobile"),
    View.OnClickListener, SubNode.NodeListener {

    private var webView: WebView? = null
    private var btnJoystickSingle: ImageButton? = null
    private var btnJoystickDouble: ImageButton? = null
    private var frJoystickSingle: Movable? = null
    private var frJoystickDouble: Movable? = null
    private var disabled: ImageButton? = null
    private var buttonViewMode: Button? = null
    private var cameraView: CameraView? = null
    private var viz2dView: Viz2DView? = null
    private var nodeTeleop: NodeTeleop? = null
    private var cameraNode: SubNode? = null
    private var viz2dNode: SubNode? = null
    private var nodeConfiguration: NodeConfiguration? = null
    private var frameTransformTree = TransformProvider.getInstance().tree

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        val defaultRobotName = getString(R.string.default_robot)
        val defaultAppName = getString(R.string.default_app)
        setDefaultMasterName(defaultRobotName)
        setDefaultAppName(defaultAppName)
        setDashboardResource(R.id.top_bar)
        setMainWindowResource(R.layout.activity_avrora_unior_control)

        super.onCreate(savedInstanceState)

        btnJoystickSingle = findViewById(R.id.btn_joystick_single)
        btnJoystickSingle?.setOnClickListener(this)

        btnJoystickDouble = findViewById(R.id.btn_joystick_double)
        btnJoystickDouble?.setOnClickListener(this)

        cameraView = findViewById(R.id.camera_view)
        viz2dView = findViewById(R.id.viz2d_view)

        //ViewGroup sideLayout = findViewById(R.id.side_layout);
        frJoystickSingle =
            JoystickSingleFragment()
        frJoystickDouble =
            JoystickDoubleFragment()

        buttonViewMode = findViewById(R.id.btn_view_mode)

        nodeTeleop = NodeTeleop(Constants.TOPIC_JOY_TELEOP, this)
        cameraNode = SubNode(this)
        viz2dNode = SubNode(this)
        cameraNode?.setWidget(CameraEntity())
        viz2dView?.widgetEntity = Viz2DEntity()
        val gridMapView = GridMapView(this)
        val poseView = PoseView(this)
        val gridMapEntity = GridMapEntity()
        val poseEntity = PoseEntity()
        gridMapView.widgetEntity = gridMapEntity
        //poseView.widgetEntity = poseEntity
        viz2dView?.addLayer(gridMapView)
        //viz2dView?.addLayer(poseView)
        viz2dNode?.setWidget(gridMapEntity)
        //viz2dNode?.setWidget(poseEntity)
        setControls(btnJoystickSingle, frJoystickSingle)

        /*webView = findViewById(R.id.webView)

        val webSettings: WebSettings? = webView?.settings
        webSettings?.javaScriptEnabled = true
        webSettings?.domStorageEnabled = true
        webView?.addJavascriptInterface(WebAppInterface(this), "Android")

        webView?.loadUrl("file:///android_asset/avrora_unior_control_feature.html")*/
    }

    override fun init(nodeMainExecutor: NodeMainExecutor?) {
        super.init(nodeMainExecutor)
        nodeConfiguration = NodeConfiguration.newPublic(
            InetAddressFactory
                .newNonLoopback().hostAddress, masterUri
        )

        nodeMainExecutor
            ?.execute(nodeTeleop, nodeConfiguration?.setNodeName("android/virtual_joystick"))
        nodeMainExecutorService.execute(cameraNode, nodeConfiguration)

        buttonViewMode?.setOnClickListener { v: View? ->
            val popup = PopupMenu(this, v)
            popup.menuInflater.inflate(R.menu.menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.mode_camera) {
                    nodeMainExecutorService.shutdownNodeMain(viz2dNode)
                    nodeMainExecutorService.execute(cameraNode, nodeConfiguration)
                    cameraView?.visibility = View.VISIBLE
                    viz2dView?.visibility = View.GONE
                    buttonViewMode?.text = getString(R.string.camera)
                } else if (item.itemId == R.id.mode_map) {
                    nodeMainExecutorService.shutdownNodeMain(cameraNode)
                    nodeMainExecutorService.execute(viz2dNode, nodeConfiguration)
                    cameraView?.visibility = View.GONE
                    viz2dView?.visibility = View.VISIBLE
                    buttonViewMode?.text = getString(R.string.map)
                }
                true
            }
            popup.show()
        }

        var timeProvider: TimeProvider?
        try {
            val ntpTimeProvider = NtpTimeProvider(
                InetAddressFactory.newFromHostString("pool.ntp.org"),
                nodeMainExecutor?.scheduledExecutorService
            )
            ntpTimeProvider.startPeriodicUpdates(1, TimeUnit.MINUTES)
            timeProvider = ntpTimeProvider
        } catch (t: Throwable) {
            Log.w(
                TAG,
                "Unable to use NTP provider, using Wall Time. Error: " + t.message,
                t
            )
            timeProvider = WallTimeProvider()
        }
        nodeConfiguration?.setTimeProvider(timeProvider)

    }

    private fun loadFragment(movable: Movable?) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.layout_controls, movable as Fragment)
            .commit()
        nodeTeleop?.setMovable(movable)
    }

    override fun onClick(view: View) {
        disabled?.isEnabled = true
        val id: Int = view.id
        if (id == R.id.btn_joystick_single) {
            setControls(btnJoystickSingle, frJoystickSingle)
        } else if (id == R.id.btn_joystick_double) {
            setControls(btnJoystickDouble, frJoystickDouble)
        }
    }

    private fun setControls(imageButton: ImageButton?, movable: Movable?) {
        imageButton?.isEnabled = false
        loadFragment(movable)
        disabled = imageButton
    }

    override fun onNewMessage(message: RosData?) {
        // Save transforms from tf messages
        if (message?.message is TFMessage) {
            val tf: TFMessage = message.message as TFMessage

            for (transform in tf.transforms) {
                var child = transform.childFrameId
                var parent = transform.header.frameId

                child = sanitizeFrameName(child)
                parent = sanitizeFrameName(parent)

                transform.childFrameId = child
                transform.header.frameId = parent
                frameTransformTree.update(transform)
            }
        }

        if (message != null) {
            onNewData(message)
        }
    }

    private fun sanitizeFrameName(name: String?): String? {
        if (name.isNullOrEmpty()) return name

        if (Character.isDigit(name[0])) {
            return "f_$name"
        }

        return name
    }

    private fun onNewData(data: RosData) {
        val message = data.message

        cameraView?.onNewMessage(message)
        viz2dView?.onNewData(data)
    }
}
