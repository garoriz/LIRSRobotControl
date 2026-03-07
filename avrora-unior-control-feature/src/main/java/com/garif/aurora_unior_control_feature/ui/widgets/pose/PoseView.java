package com.garif.aurora_unior_control_feature.ui.widgets.pose;

import android.content.Context;

import com.garif.aurora_unior_control_feature.model.entities.widgets.BaseEntity;
import com.garif.aurora_unior_control_feature.model.repositories.TransformProvider;
import com.garif.aurora_unior_control_feature.ui.opengl.shape.GoalShape;
import com.garif.aurora_unior_control_feature.ui.opengl.shape.Shape;
import com.garif.aurora_unior_control_feature.ui.opengl.visualisation.VisualizationView;
import com.garif.aurora_unior_control_feature.ui.views.widgets.SubscriberLayerView;

import org.ros.internal.message.Message;
import org.ros.namespace.GraphName;
import org.ros.rosjava_geometry.FrameTransform;
import org.ros.rosjava_geometry.Transform;

import javax.microedition.khronos.opengles.GL10;

import geometry_msgs.PoseWithCovarianceStamped;

public class PoseView extends SubscriberLayerView {

    public static final String TAG = PoseView.class.getSimpleName();

    private Shape shape;
    private PoseWithCovarianceStamped pose;


    public PoseView(Context context) {
        super(context);
        shape = new GoalShape();
    }


    public void setWidgetEntity(BaseEntity widgetEntity) {
        super.setWidgetEntity(widgetEntity);
    }

    @Override
    public void draw(VisualizationView view, GL10 gl) {
        if (pose == null) return;

        shape.draw(view, gl);
    }

    @Override
    public void onNewMessage(Message message) {
        pose = (PoseWithCovarianceStamped)message;

        GraphName source = GraphName.of(pose.getHeader().getFrameId());
        frame = source;
        FrameTransform frameTransform = TransformProvider.getInstance().getTree().transform(source, frame);

        if (frameTransform == null) return;

        Transform poseTransform = Transform.fromPoseMessage(pose.getPose().getPose());
        shape.setTransform(frameTransform.getTransform().multiply(poseTransform));
    }
}
