package com.garif.aurora_unior_control_feature.ui.views.widgets;

import com.garif.aurora_unior_control_feature.model.repositories.BaseData;
import com.garif.aurora_unior_control_feature.ui.general.DataListener;

public interface IPublisherView {

    void publishViewData(BaseData data);

    void setDataListener(DataListener listener);
}
