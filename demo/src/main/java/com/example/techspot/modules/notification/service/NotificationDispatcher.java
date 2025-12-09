package com.example.techspot.modules.notification.service;


import com.example.techspot.modules.notification.event.NotificationEvent;
import com.example.techspot.modules.notification.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationDispatcher {

	private final List<NotificationStrategy> strategies;


	@EventListener
	public void handler(NotificationEvent event){

		System.out.println("NotificationDispatcher В ДЕЛЕ ЕЕЕ БОЙ");
		strategies.stream()
				.filter(s -> s.support(event))
				.findFirst()
				.ifPresent(s -> s.send(event));
	}
}
