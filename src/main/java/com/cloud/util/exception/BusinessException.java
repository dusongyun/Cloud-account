package com.cloud.util.exception;

public class BusinessException extends AbstractGenericException {
	private static final long serialVersionUID = 3278974541205010695L;

	public BusinessException(String errorCode, String message) {
		super(errorCode, message);
	}

	public BusinessException(String errorCode, String message,
			Throwable throwable) {
		super(errorCode, message, throwable);
	}

	public BusinessException(String errorCode, Object[] arguments) {
		super(errorCode, arguments);
	}

	public BusinessException(String code, Throwable throwable,
			Object[] arguments) {
		super(code, throwable, arguments);
	}

	public BusinessException(String code, Throwable throwable) {
		super(code, throwable);
	}

	public BusinessException(String errorCode) {
		super(errorCode);
	}

}