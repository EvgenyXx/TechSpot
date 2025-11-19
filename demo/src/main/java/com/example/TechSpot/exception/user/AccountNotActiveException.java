package com.example.TechSpot.exception.user;

import com.example.TechSpot.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AccountNotActiveException extends BaseException {

	public AccountNotActiveException (){
		super(
				"Ваш аккаунт заблокирован. Для разблокировки обратитесь в службу поддержки ",
				HttpStatus.LOCKED,
				"ACCOUNT_NOT_ACTIVE"

		);
	}
}
