package com.dan.chatserver.controller;

import com.dan.chatserver.types.ChatException;

public class ChatControllerException extends ChatException {
	private static final long serialVersionUID = 6272516624353653371L;

	public ChatControllerException(int statusCode, ChatControllerErrorCode errorCode) {
		super(statusCode, errorCode.name());
	}
}

enum ChatControllerErrorCode {
	INVALID_ROOM_PASSWORD
}

class ChatControllerForbiddenException extends ChatControllerException {
	private static final long serialVersionUID = -4226872699861280330L;

	public ChatControllerForbiddenException(ChatControllerErrorCode errorCode) {
		super(403, errorCode);
	}
}