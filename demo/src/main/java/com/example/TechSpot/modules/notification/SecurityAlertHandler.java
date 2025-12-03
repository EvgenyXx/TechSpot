package com.example.TechSpot.modules.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityAlertHandler {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;

	@Async
	@EventListener
	public void handleSuspiciousLogin(SuspiciousLoginAttemptEvent event) {
		log.warn("🚨 Подозрительная активность для: {}", event.email());
		sendSecurityEmail(event);
	}

	private void sendSecurityEmail(SuspiciousLoginAttemptEvent event) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			// Отправитель
			helper.setFrom("TechSpot Security <EvgenyPavlov666@yandex.ru>");

			// Получатель (владелец аккаунта)
			helper.setTo(event.email());

			// Тема
			helper.setSubject("⚠️ TechSpot: Обнаружена подозрительная активность");

			// Генерация HTML из шаблона Thymeleaf
			Context context = new Context();
			context.setVariable("email", event.email());
			context.setVariable("failedAttempts", event.failedAttempts());
			context.setVariable("ipAddress", event.ipAddress());
			context.setVariable("timestamp",
					event.timestamp().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
			context.setVariable("userAgent", shortenUserAgent(event.userAgent()));
			context.setVariable("deviceType", getDeviceType(event.userAgent()));

			String htmlContent = templateEngine.process("emails/security-alert", context);

			// Устанавливаем HTML контент
			helper.setText(htmlContent, true);

			// Добавляем логотип
			helper.addInline("logo",
					new ClassPathResource("static/images/TechSpot-logo-transparent.png"));

			mailSender.send(mimeMessage);
			log.info("✅ Security email sent to: {}", event.email());

		} catch (Exception e) {
			log.error("❌ Ошибка отправки security email: {}", e.getMessage());
		}
	}

	private String shortenUserAgent(String userAgent) {
		if (userAgent == null || userAgent.isEmpty()) {
			return "Неизвестное устройство";
		}

		// Определяем браузер
		if (userAgent.contains("Chrome")) return "Chrome";
		if (userAgent.contains("Firefox")) return "Firefox";
		if (userAgent.contains("Safari")) return "Safari";
		if (userAgent.contains("Edge")) return "Microsoft Edge";
		if (userAgent.contains("Opera")) return "Opera";

		// Определяем устройство
		if (userAgent.contains("Mobile")) return "Мобильное устройство";
		if (userAgent.contains("Android")) return "Android устройство";
		if (userAgent.contains("iPhone")) return "iPhone";
		if (userAgent.contains("iPad")) return "iPad";
		if (userAgent.contains("Windows")) return "Windows компьютер";
		if (userAgent.contains("Mac OS")) return "Mac компьютер";
		if (userAgent.contains("Linux")) return "Linux компьютер";

		// Обрезаем слишком длинные строки
		return userAgent.length() > 50
				? userAgent.substring(0, 47) + "..."
				: userAgent;
	}

	private String getDeviceType(String userAgent) {
		if (userAgent == null) return "Неизвестно";

		if (userAgent.contains("Mobile") ||
				userAgent.contains("Android") ||
				userAgent.contains("iPhone") ||
				userAgent.contains("iPad")) {
			return "Мобильное устройство";
		}

		if (userAgent.contains("Windows") ||
				userAgent.contains("Mac OS") ||
				userAgent.contains("Linux")) {
			return "Компьютер";
		}

		return "Неизвестное устройство";
	}
}