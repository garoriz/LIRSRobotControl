package com.garif.aurora_unior_control_feature.ui.activities

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageButton
import com.garif.aurora_unior_control_feature.Constants
import com.garif.aurora_unior_control_feature.R
import com.garif.aurora_unior_control_feature.nodes.NodeTeleop
import com.garif.core.Movable
import com.garif.core.fragments.JoystickDoubleFragment
import com.garif.core.fragments.JoystickSingleFragment
import com.github.rosjava.android_remocons.common_tools.apps.RosAppActivity
import org.ros.address.InetAddressFactory
import org.ros.node.NodeConfiguration
import org.ros.node.NodeMainExecutor
import org.ros.time.NtpTimeProvider
import org.ros.time.TimeProvider
import org.ros.time.WallTimeProvider
import java.util.concurrent.TimeUnit

private const val TAG: String = "MapNav"

class AvroraUniorControlActivity : RosAppActivity("AvroraUniorMobile", "AvroraUniorMobile"),
    View.OnClickListener {

    private var webView: WebView? = null
    private var btnJoystickSingle: ImageButton? = null
    private var btnJoystickDouble: ImageButton? = null
    private var frJoystickSingle: Movable? = null
    private var frJoystickDouble: Movable? = null
    private var disabled: ImageButton? = null
    private var nodeTeleop: NodeTeleop? = null
    private var nodeConfiguration: NodeConfiguration? = null

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

        //ViewGroup sideLayout = findViewById(R.id.side_layout);
        frJoystickSingle = JoystickSingleFragment()
        frJoystickDouble = JoystickDoubleFragment()

        nodeTeleop = NodeTeleop(Constants.TOPIC_JOY_TELEOP, this)
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
}
