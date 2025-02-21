package com.garif.pmb2_control_feature.ui.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.garif.pmb2_control_feature.R;
import com.garif.pmb2_control_feature.layers.InitialPoseSubscriberLayer;
import com.garif.pmb2_control_feature.layers.MapPosePublisherLayer;
import com.garif.pmb2_control_feature.layers.ViewControlLayer;
import com.garif.pmb2_control_feature.nodes.NodeTeleop;
import com.garif.pmb2_control_feature.ui.fragments.JoystickSingleFragment;
import com.garif.pmb2_control_feature.utils.Constants;
import com.garif.pmb2_control_feature.utils.MapManager;
import com.garif.pmb2_control_feature.utils.Movable;
import com.github.rosjava.android_remocons.common_tools.apps.RosAppActivity;
import com.google.common.collect.Lists;

import org.ros.address.InetAddressFactory;
import org.ros.android.BitmapFromCompressedImage;
import org.ros.android.view.RosImageView;
import org.ros.android.view.visualization.VisualizationView;
import org.ros.android.view.visualization.layer.CameraControlListener;
import org.ros.android.view.visualization.layer.LaserScanLayer;
import org.ros.android.view.visualization.layer.OccupancyGridLayer;
import org.ros.android.view.visualization.layer.PathLayer;
import org.ros.exception.RemoteException;
import org.ros.namespace.NameResolver;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;
import org.ros.node.service.ServiceResponseListener;
import org.ros.time.NtpTimeProvider;
import org.ros.time.TimeProvider;
import org.ros.time.WallTimeProvider;

import java.sql.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import sensor_msgs.CompressedImage;
import world_canvas_msgs.ListMapsResponse;
import world_canvas_msgs.MapListEntry;
import world_canvas_msgs.PublishMapResponse;

public class Pmb2ControlActivity extends RosAppActivity implements View.OnClickListener {

    private static final String TAG = "MapNav";

    private NodeTeleop nodeTeleop;
    private ViewControlLayer viewControlLayer;
    private RosImageView<CompressedImage> cameraView;
    private VisualizationView mapView;
    private MapPosePublisherLayer mapPosePublisherLayer;
    private ProgressDialog waitingDialog;
    private AlertDialog chooseMapDialog;
    private NodeMainExecutor nodeMainExecutor;
    private NodeConfiguration nodeConfiguration;

    public Pmb2ControlActivity() {
        super("PMB2Mobile", "PMB2Mobile");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        String defaultRobotName = getString(R.string.default_robot);
        String defaultAppName = getString(R.string.default_app);
        setDefaultMasterName(defaultRobotName);
        setDefaultAppName(defaultAppName);
        setDashboardResource(R.id.top_bar);
        setMainWindowResource(R.layout.activity_pmb2_control);
        super.onCreate(savedInstanceState);

        cameraView = findViewById(R.id.image);
        cameraView.setMessageType(sensor_msgs.CompressedImage._TYPE);
        cameraView.setMessageToBitmapCallable(new BitmapFromCompressedImage());

        ImageButton btnShowMap = findViewById(R.id.btn_show_map);
        btnShowMap.setOnClickListener(view -> showMap());

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(Lists.newArrayList());

        ViewGroup mainLayout = findViewById(R.id.main_layout);
        ViewGroup sideLayout = findViewById(R.id.side_layout);

        Movable frJoystickSingle = new JoystickSingleFragment();

        viewControlLayer = new ViewControlLayer(
                this, cameraView, mapView, mainLayout, sideLayout, params);

        nodeTeleop = new NodeTeleop(Constants.TOPIC_JOY_TELEOP);
        loadFragment(frJoystickSingle);
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        super.init(nodeMainExecutor);

        this.nodeMainExecutor = nodeMainExecutor;
        nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory
                .newNonLoopback().getHostAddress(), getMasterUri());

        String topicJoy = remaps.get(getString(R.string.joystick_topic));
        String topicCam = remaps.get(getString(R.string.camera_topic));

        NameResolver appNameSpace = getMasterNameSpace();
        cameraView.setTopicName(appNameSpace.resolve(topicCam).toString());
//        nodeTeleop = new NodeTeleop(appNameSpace.resolve(topicJoy).toString());

        nodeMainExecutor
                .execute(cameraView, nodeConfiguration.setNodeName("android/camera_view"));

        nodeMainExecutor
                .execute(nodeTeleop, nodeConfiguration.setNodeName("android/virtual_joystick"));

        viewControlLayer.addExecutorService(nodeMainExecutor.getScheduledExecutorService());

        String mapTopic = remaps.get(getString(R.string.map_topic));
        String costMapTopic = remaps.get(getString(R.string.costmap_topic));
        String scanTopic = remaps.get(getString(R.string.scan_topic));
        String planTopic = remaps.get(getString(R.string.global_plan_topic));
        String initTopic = remaps.get(getString(R.string.initial_pose_topic));
        String robotFrame = (String) params.get("robot_frame", getString(R.string.robot_frame));

        OccupancyGridLayer mapLayer = new OccupancyGridLayer(appNameSpace.resolve(mapTopic).toString());
        OccupancyGridLayer costMapLayer = new OccupancyGridLayer(appNameSpace.resolve(costMapTopic).toString());
        LaserScanLayer laserScanLayer = new LaserScanLayer(appNameSpace.resolve(scanTopic).toString());
        PathLayer pathLayer = new PathLayer(appNameSpace.resolve(planTopic).toString());
        mapPosePublisherLayer = new MapPosePublisherLayer(this, appNameSpace, params, remaps);
        InitialPoseSubscriberLayer initialPoseSubscriberLayer =
                new InitialPoseSubscriberLayer(appNameSpace.resolve(initTopic).toString(), robotFrame);

        mapView.addLayer(viewControlLayer);
        mapView.addLayer(mapLayer);
        mapView.addLayer(costMapLayer);
        mapView.addLayer(laserScanLayer);
        mapView.addLayer(pathLayer);
        mapView.addLayer(mapPosePublisherLayer);
        mapView.addLayer(initialPoseSubscriberLayer);

        mapView.init(nodeMainExecutor);
        viewControlLayer.addListener(new CameraControlListener() {
            @Override
            public void onZoom(float focusX, float focusY, float factor) {
            }

            @Override
            public void onDoubleTap(float x, float y) {
            }

            @Override
            public void onTranslate(float distanceX, float distanceY) {
            }

            @Override
            public void onRotate(float focusX, float focusY, double deltaAngle) {
            }
        });

        TimeProvider timeProvider;
        try {
            NtpTimeProvider ntpTimeProvider = new NtpTimeProvider(
                    InetAddressFactory.newFromHostString("pool.ntp.org"),
                    nodeMainExecutor.getScheduledExecutorService());
            ntpTimeProvider.startPeriodicUpdates(1, TimeUnit.MINUTES);
            timeProvider = ntpTimeProvider;
        } catch (Throwable t) {
            Log.w(TAG, "Unable to use NTP provider, using Wall Time. Error: " + t.getMessage(), t);
            timeProvider = new WallTimeProvider();
        }
        nodeConfiguration.setTimeProvider(timeProvider);
        nodeMainExecutor.execute(mapView, nodeConfiguration.setNodeName("android/map_view"));
    }

    private void showMap() {
        mapView.getCamera().jumpToFrame((String) params.get("map_frame", getString(R.string.map_frame)));
    }

    private void onChooseMapButtonPressed() {
        readAvailableMapList();
    }

    public void setPoseClicked(View view) {
        setPose();
    }

    public void setGoalClicked(View view) {
        setGoal();
    }

    private void setPose() {
        mapPosePublisherLayer.setPoseMode();
    }

    private void setGoal() {
        mapPosePublisherLayer.setGoalMode();
    }

    private void readAvailableMapList() {
        safeShowWaitingDialog("Waiting for map list");

        MapManager mapManager = new MapManager(this, remaps);
        mapManager.setNameResolver(getMasterNameSpace());
        mapManager.setFunction("list");
        safeShowWaitingDialog("Waiting for map list");
        mapManager.setListService(new ServiceResponseListener<ListMapsResponse>() {
            @Override
            public void onSuccess(ListMapsResponse message) {
                Log.i(TAG, "readAvailableMapList() Success");
                safeDismissWaitingDialog();
                showMapListDialog(message.getMapList());
            }

            @Override
            public void onFailure(RemoteException e) {
                Log.i(TAG, "readAvailableMapList() Failure");
                safeDismissWaitingDialog();
            }
        });

        nodeMainExecutor.execute(mapManager,
                nodeConfiguration.setNodeName("android/list_maps"));
    }

    /**
     * Show a dialog with a list of maps. Safe to call from any thread.
     */
    private void showMapListDialog(final List<MapListEntry> list) {
        // Make an array of map name/date strings.
        final CharSequence[] availableMapNames = new CharSequence[list.size()];
        for (int i = 0; i < list.size(); i++) {
            String displayString;
            String name = list.get(i).getName();
            Date creationDate = new Date(list.get(i).getDate() * 1000);
            String dateTime = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                    DateFormat.SHORT).format(creationDate);
            if (name != null && !name.isEmpty()) {
                displayString = name + " " + dateTime;
            } else {
                displayString = dateTime;
            }
            availableMapNames[i] = displayString;
        }

        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    Pmb2ControlActivity.this);
            builder.setTitle("Choose a map");
            builder.setItems(availableMapNames,
                    (dialog, itemIndex) ->
                            loadMap(list.get(itemIndex)));
            chooseMapDialog = builder.create();
            chooseMapDialog.show();
        });
    }

    private void loadMap(MapListEntry mapListEntry) {

        MapManager mapManager = new MapManager(this, remaps);
        mapManager.setNameResolver(getMasterNameSpace());
        mapManager.setFunction("publish");
        mapManager.setMapId(mapListEntry.getMapId());

        safeShowWaitingDialog("Loading map");
        try {
            mapManager
                    .setPublishService(new ServiceResponseListener<PublishMapResponse>() {
                        @Override
                        public void onSuccess(PublishMapResponse message) {
                            Log.i(TAG, "loadMap() Success");
                            safeDismissWaitingDialog();
                            // poseSetter.enable();
                        }

                        @Override
                        public void onFailure(RemoteException e) {
                            Log.i(TAG, "loadMap() Failure");
                            safeDismissWaitingDialog();
                        }
                    });
        } catch (Throwable ex) {
            Log.e(TAG, "loadMap() caught exception.", ex);
            safeDismissWaitingDialog();
        }
        nodeMainExecutor.execute(mapManager,
                nodeConfiguration.setNodeName("android/publish_map"));
    }

    private void safeDismissChooseMapDialog() {
        runOnUiThread(() -> {
            if (chooseMapDialog != null) {
                chooseMapDialog.dismiss();
                chooseMapDialog = null;
            }
        });
    }

    private void showWaitingDialog(final CharSequence message) {
        dismissWaitingDialog();
        waitingDialog = ProgressDialog.show(Pmb2ControlActivity.this, "Waiting...", message,
                true);
        waitingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void dismissWaitingDialog() {
        if (waitingDialog != null) {
            waitingDialog.dismiss();
            waitingDialog = null;
        }
    }

    private void safeShowWaitingDialog(final CharSequence message) {
        runOnUiThread(() -> showWaitingDialog(message));
    }

    private void safeDismissWaitingDialog() {
        runOnUiThread(this::dismissWaitingDialog);
    }

    private void loadFragment(Movable movable) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_controls, (Fragment) movable)
                .commit();
        nodeTeleop.setMovable(movable);
    }

    @Override
    public void onClick(View view) {
    }
}
