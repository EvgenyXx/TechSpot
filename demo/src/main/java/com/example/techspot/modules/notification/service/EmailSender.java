package com.example.techspot.modules.notification.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;

	public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom("TechSpot <EvgenyPavlov666@yandex.ru>");
			helper.setTo(to);
			helper.setSubject(subject);

			Context context = new Context();
			context.setVariables(variables);

			String htmlContent = templateEngine.process(templateName, context);
			helper.setText(htmlContent, true);

			mailSender.send(mimeMessage);

			log.info("Email успешно отправлен на {}", to);

		} catch (Exception e) {
			log.error("Ошибка отправки email: {}", e.getMessage(), e);
			throw new RuntimeException("Ошибка отправки email", e);
		}
	}
}
