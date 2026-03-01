package com.garif.aurora_unior_control_feature.ui.views.widgets;

import com.garif.aurora_unior_control_feature.model.entities.widgets.BaseEntity;

public interface IBaseView {

    void setWidgetEntity(BaseEntity entity);

    BaseEntity getWidgetEntity();

    boolean sameWidgetEntity(BaseEntity other);
}
