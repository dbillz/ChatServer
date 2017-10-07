package com.dan.chatserver.types;

public abstract class ChatException extends Exception {

	private static final long serialVersionUID = -6514137604164632537L;
	private final int statusCode;
	private final String errorCode;
	
	public ChatException (int statusCode, String errorCode) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
}
