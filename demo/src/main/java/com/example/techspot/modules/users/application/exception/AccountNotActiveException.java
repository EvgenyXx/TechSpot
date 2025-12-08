package com.example.techspot.modules.users.application.exception;

import com.example.techspot.common.exception.BaseException;
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
