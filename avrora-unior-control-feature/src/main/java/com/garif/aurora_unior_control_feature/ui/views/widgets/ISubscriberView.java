package com.garif.aurora_unior_control_feature.ui.views.widgets;

import org.ros.internal.message.Message;

public interface ISubscriberView {

    void onNewMessage(Message message);
}
