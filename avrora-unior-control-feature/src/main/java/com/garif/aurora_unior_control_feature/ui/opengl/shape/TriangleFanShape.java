/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.garif.aurora_unior_control_feature.ui.opengl.shape;

import com.garif.aurora_unior_control_feature.ui.opengl.visualisation.ROSColor;
import com.garif.aurora_unior_control_feature.ui.opengl.visualisation.Vertices;
import com.garif.aurora_unior_control_feature.ui.opengl.visualisation.VisualizationView;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class TriangleFanShape extends BaseShape {

    private final FloatBuffer vertices;

    /**
     * @param vertices an array of vertices as defined by OpenGL's GL_TRIANGLE_FAN method
     * @param color    the {@link ROSColor} of the {@link Shape}
     */
    public TriangleFanShape(float[] vertices, ROSColor color) {
        super();
        this.vertices = Vertices.toFloatBuffer(vertices);
        setColor(color);
    }

    @Override
    public void drawShape(VisualizationView view, GL10 gl) {
        Vertices.drawTriangleFan(gl, vertices, getColor());
    }
}
