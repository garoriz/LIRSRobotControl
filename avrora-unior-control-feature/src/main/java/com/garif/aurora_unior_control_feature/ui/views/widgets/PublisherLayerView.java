package com.garif.aurora_unior_control_feature.ui.views.widgets;

import android.content.Context;

import com.garif.aurora_unior_control_feature.model.repositories.BaseData;
import com.garif.aurora_unior_control_feature.ui.general.DataListener;
import com.garif.aurora_unior_control_feature.ui.opengl.visualisation.VisualizationView;

import javax.microedition.khronos.opengles.GL10;

public abstract class PublisherLayerView extends LayerView implements IPublisherView {

    private DataListener dataListener;


    public PublisherLayerView(Context context) {
        super(context);
    }


    @Override
    public void publishViewData(BaseData data) {
        if (dataListener == null) return;

        data.setTopic(widgetEntity.topic);
        dataListener.onNewWidgetData(data);
    }

    @Override
    public void setDataListener(DataListener listener) {
        this.dataListener = listener;
    }

    @Override
    public void draw(VisualizationView view, GL10 gl) {
    }
}
