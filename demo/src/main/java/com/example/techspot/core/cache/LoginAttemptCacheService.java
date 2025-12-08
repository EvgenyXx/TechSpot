package com.example.techspot.core.cache;

import com.example.techspot.core.config.RedisProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Log4j2
@Service
public class LoginAttemptCacheService {

	private final RedisProperties redisProperties;



	private final StringRedisTemplate redisTemplate;


	public void loginFailed(String email) {
		log.info("Неудачная попытка входа для: {}", email);

		String prefix = redisProperties.getLogin().getLoginAttemptsPrefix();
		Duration ddl = redisProperties.getLogin().getLoginAttemptsTtl();

		String key = prefix + email;
		log.debug("Ключ: {}, TTL: {} минут", key, ddl.toMinutes());

		Long attempts = redisTemplate.opsForValue().increment(key);
		log.debug("Счетчик увеличен: {}", attempts);

		if (attempts != null && attempts == 1){
			redisTemplate.expire(key,ddl.toMinutes(), TimeUnit.MINUTES);
			log.debug("TTL установлен");
		}

		log.warn("Попытка входа {} для {}", attempts, email);
	}

	public boolean isBlocked(String email){
		String prefix = redisProperties.getLogin().getLoginAttemptsPrefix();
		String key = prefix + email;

		// 👇 ПОЛУЧАЕМ СТРОКУ И КОНВЕРТИРУЕМ В ЧИСЛО
		String attemptsStr = redisTemplate.opsForValue().get(key);
		Integer attempts = attemptsStr != null ? Integer.parseInt(attemptsStr) : null;

		boolean blocked = attempts != null && attempts >= redisProperties.getLogin().getAttemptsFailed();

		if (blocked) {
			log.warn("🚫 АККАУНТ ЗАБЛОКИРОВАН: {}. Попыток: {}", email, attempts);
		}

		return blocked;
	}


	public void loginSuccess(String email) {
		log.info("Сброс счетчика попыток для: {}", email);

		String prefix = redisProperties.getLogin().getLoginAttemptsPrefix();
		String key = prefix + email;

		redisTemplate.delete(key);
		log.debug("Ключ удален: {}", key);
	}

}