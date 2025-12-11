package com.example.techspot.modules.auth.port;

import com.example.techspot.modules.notification.event.PasswordResetEvent;

public interface PasswordResetPublisher {

	void publishPasswordReset(PasswordResetEvent passwordResetEvent);
}
