package com.garif.aurora_unior_control_feature.ui.opengl.shape;

import com.garif.aurora_unior_control_feature.ui.opengl.visualisation.ROSColor;
import com.garif.aurora_unior_control_feature.ui.opengl.visualisation.VisualizationView;
import com.google.common.base.Preconditions;

import org.ros.android.view.visualization.OpenGlTransform;
import org.ros.rosjava_geometry.Transform;

import javax.microedition.khronos.opengles.GL10;

abstract class BaseShape implements Shape {

    private ROSColor color;
    private Transform transform;

    public BaseShape() {
        setTransform(Transform.identity());
    }

    @Override
    public void draw(VisualizationView view, GL10 gl) {
        gl.glPushMatrix();
        OpenGlTransform.apply(gl, getTransform());
        scale(view, gl);
        drawShape(view, gl);
        gl.glPopMatrix();
    }

    /**
     * To be implemented by children. Draws the shape after the shape's
     * transform and scaling have been applied.
     */
    abstract protected void drawShape(VisualizationView view, GL10 gl);

    /**
     * Scales the coordinate system.
     * <p>
     * This is called after transforming the surface according to
     * {@link #transform}.
     */
    protected void scale(VisualizationView view, GL10 gl) {
        // The default scale is in metric space.
    }

    @Override
    public ROSColor getColor() {
        Preconditions.checkNotNull(color);
        return color;
    }

    @Override
    public void setColor(ROSColor color) {
        this.color = color;
    }

    @Override
    public Transform getTransform() {
        Preconditions.checkNotNull(transform);
        return transform;
    }

    @Override
    public void setTransform(Transform pose) {
        this.transform = pose;
    }
}
