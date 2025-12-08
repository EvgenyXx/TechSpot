package com.example.techspot.modules.api.user;

public interface PasswordResetProvider {


	String generateAndSetResetCode(String email);
	void resetPassword(String email, String resetCode, String newPassword);
	void validateResetCode(String email, String resetCode);

}
