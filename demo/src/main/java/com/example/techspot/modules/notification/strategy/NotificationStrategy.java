package com.example.techspot.modules.notification.strategy;

import com.example.techspot.modules.notification.event.NotificationEvent;

public interface NotificationStrategy {

	boolean support(NotificationEvent notificationEvent);

	void send (NotificationEvent notificationEvent);
}
