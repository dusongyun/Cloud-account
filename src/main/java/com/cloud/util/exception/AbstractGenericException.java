package com.cloud.util.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

public abstract class AbstractGenericException extends Exception implements
		Serializable {
	private static final long serialVersionUID = -6785244690920409384L;
	protected String errorCode;
	protected String message;
	private Object[] arguments;
	private String fullStackTrace;

	public AbstractGenericException(String errorCode) {
		this.errorCode = errorCode;
	}

	public AbstractGenericException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	public AbstractGenericException(Throwable cause) {
		super(cause);
	}

	public AbstractGenericException(String errorCode, String message,
			Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public AbstractGenericException(String code, Throwable throwable) {
		super(code, throwable);
		this.errorCode = code;
	}

	public AbstractGenericException(String errorCode, Object[] arguments) {
		this.errorCode = errorCode;
		this.arguments = arguments;
		setMessage(errorCode, arguments);
	}

	public AbstractGenericException(String code, Throwable throwable,
			Object[] arguments) {
		super(code, throwable);
		this.errorCode = code;
		this.arguments = arguments;
		setMessage(code, arguments);
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getMessage() {
		return this.message;
	}

	public Object[] getArguments() {
		return this.arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public void setMessage(String code, Object[] args) {
		StringBuilder sb = new StringBuilder(code);
		for (Object arg : args) {
			sb.append(" ").append(arg);
		}
		this.message = sb.toString();
	}

	public void initFullStackTrace() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		printStackTrace(ps);
		ps.close();
		this.fullStackTrace = bos.toString();
	}

	public String getFullStackTrace() {
		if (this.fullStackTrace == null) {
			initFullStackTrace();
		}
		return this.fullStackTrace;
	}

	public static String getFullStaceTrace(Exception e) {
		if (e == null) {
			return null;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		e.printStackTrace(ps);
		ps.close();
		return bos.toString();
	}
}