package com.garif.pmb2_control_feature.layers;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewGroup;

import com.garif.pmb2_control_feature.R;
import com.github.rosjava.android_remocons.common_tools.apps.AppParameters;

import org.ros.android.view.RosImageView;
import org.ros.android.view.visualization.RotateGestureDetector;
import org.ros.android.view.visualization.VisualizationView;
import org.ros.android.view.visualization.layer.CameraControlLayer;
import org.ros.android.view.visualization.layer.CameraControlListener;
import org.ros.concurrent.ListenerGroup;
import org.ros.node.ConnectedNode;

import java.util.concurrent.ExecutorService;

import sensor_msgs.CompressedImage;

/**
 * @author murase@jsk.imi.i.u-tokyo.ac.jp (Kazuto Murase)
 */
public class ViewControlLayer extends CameraControlLayer {

    private final Context context;
    private ListenerGroup<CameraControlListener> listeners;

    private GestureDetector translateGestureDetector;
    private RotateGestureDetector rotateGestureDetector;
    private ScaleGestureDetector zoomGestureDetector;

    private RosImageView<CompressedImage> cameraView;
    private VisualizationView mapView;
    private ViewGroup mainLayout;
    private ViewGroup sideLayout;
    private boolean mapViewGestureAvailable;
    private String robotFrame;

    private enum ViewMode {CAMERA, MAP}

    private ViewMode viewMode;


    public ViewControlLayer(final Context context,
                            final RosImageView<sensor_msgs.CompressedImage> cameraView,
                            final VisualizationView mapView,
                            final ViewGroup mainLayout,
                            final ViewGroup sideLayout,
                            final AppParameters params) {

        this.context = context;

        this.cameraView = cameraView;
        this.mapView = mapView;
        this.mainLayout = mainLayout;
        this.sideLayout = sideLayout;

        viewMode = ViewMode.CAMERA;
        this.cameraView.setOnClickListener(v -> swapViews());

        this.mapView.setClickable(true);
        this.cameraView.setClickable(false);
        this.robotFrame = (String) params.get("robot_frame", context.getString(R.string.robot_frame));
        mapViewGestureAvailable = false;
    }

    public void addExecutorService(ExecutorService executorService) {
        listeners = new ListenerGroup<>(executorService);
    }

    @Override
    public boolean onTouchEvent(VisualizationView view, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            mapViewGestureAvailable = true;
        }
        if (viewMode == ViewMode.CAMERA) {
            swapViews();
            return true;
        } else {
            if (translateGestureDetector == null ||
                    rotateGestureDetector == null ||
                    zoomGestureDetector == null) {
                return false;
            }
            return translateGestureDetector.onTouchEvent(event) ||
                    rotateGestureDetector.onTouchEvent(event) ||
                    zoomGestureDetector.onTouchEvent(event);
        }
    }

    /**
     * Swap the camera and map views.
     */
    private void swapViews() {
        // Figure out where the views were...
        ViewGroup mapViewParent;
        ViewGroup cameraViewParent;

        if (viewMode == ViewMode.CAMERA) {
            mapViewParent = sideLayout;
            cameraViewParent = mainLayout;
        } else {
            mapViewParent = mainLayout;
            cameraViewParent = sideLayout;
        }
        int mapViewIndex = mapViewParent.indexOfChild(mapView);
        int cameraViewIndex = cameraViewParent.indexOfChild(cameraView);

        // Remove the views from their old locations...
        mapViewParent.removeView(mapView);
        cameraViewParent.removeView(cameraView);

        // Add them to their new location...
        mapViewParent.addView(cameraView, mapViewIndex);
        cameraViewParent.addView(mapView, cameraViewIndex);

        // Remeber that we are in the other mode now.
        if (viewMode == ViewMode.CAMERA) {
            viewMode = ViewMode.MAP;
            mapViewGestureAvailable = false;
        } else {
            viewMode = ViewMode.CAMERA;
        }
        mapView.getCamera().jumpToFrame(robotFrame);
        mapView.setClickable(viewMode != ViewMode.MAP);
        cameraView.setClickable(viewMode != ViewMode.CAMERA);
    }

    @Override
    public void onStart(final VisualizationView view, ConnectedNode connectedNode) {
        view.post(() -> {
            translateGestureDetector =
                    new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onScroll(MotionEvent event1, MotionEvent event2,
                                                final float distanceX, final float distanceY) {
                            if (mapViewGestureAvailable) {
                                view.getCamera().translate(-distanceX, distanceY);
                                listeners.signal(listener ->
                                        listener.onTranslate(-distanceX, distanceY));
                                return true;
                            }
                            return false;
                        }
                    });
            rotateGestureDetector =
                    new RotateGestureDetector((event1, event2, deltaAngle) -> {
                        if (mapViewGestureAvailable) {
                            final float focusX = (event1.getX(0) + event1.getX(1)) / 2;
                            final float focusY = (event1.getY(0) + event1.getY(1)) / 2;
                            view.getCamera().rotate(focusX, focusY, deltaAngle);
                            listeners.signal(listener ->
                                    listener.onRotate(focusX, focusY, deltaAngle));
                            // Don't consume this event in order to allow the zoom gesture
                            // to also be detected.
                            return false;
                        }
                        return true;
                    });
            zoomGestureDetector =
                    new ScaleGestureDetector(context,
                            new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                                @Override
                                public boolean onScale(ScaleGestureDetector detector) {
                                    if (!detector.isInProgress()) {
                                        return false;
                                    }
                                    if (mapViewGestureAvailable) {
                                        final float focusX = detector.getFocusX();
                                        final float focusY = detector.getFocusY();
                                        final float factor = detector.getScaleFactor();
                                        view.getCamera().zoom(focusX, focusY, factor);
                                        listeners.signal(listener ->
                                                listener.onZoom(focusX, focusY, factor));
                                        return true;
                                    }
                                    return false;
                                }
                            });
        });
    }
}
