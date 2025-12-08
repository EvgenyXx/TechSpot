package com.example.techspot.modules.notification;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;

	@Async
	@EventListener
	public void handlePasswordReset(PasswordResetEvent event) {
		log.info("📧 Отправка письма с логотипом на: {}", event.email());

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			// Отправитель
			helper.setFrom("TechSpot <EvgenyPavlov666@yandex.ru>");

			// Получатель
			helper.setTo(event.email());

			// Тема
			helper.setSubject("🔐 Код сброса пароля - TechSpot");

			// Генерация HTML из шаблона
			Context context = new Context();
			context.setVariable("resetCode", event.code());

			String htmlContent = templateEngine.process("emails/reset-password", context);

			// Устанавливаем HTML контент
			helper.setText(htmlContent, true);

			// ✅ Добавляем логотип
			helper.addInline("logo", new ClassPathResource("static/images/TechSpot-logo-transparent.png"));

			mailSender.send(mimeMessage);
			log.info("✅ Письмо с логотипом отправлено на: {}", event.email());

		} catch (Exception e) {
			log.error("❌ Ошибка отправки: {}", e.getMessage());
		}
	}
}