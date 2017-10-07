package com.dan.chatserver.messageDao;

import com.dan.chatserver.types.ChatException;

public abstract class MessageDaoException extends ChatException {
	private static final long serialVersionUID = -8251343988106508991L;

	public MessageDaoException(int statusCode, MessageDaoErrorCode errorCode) {
		super(statusCode, errorCode.name());
	}
}

enum MessageDaoErrorCode {
	MESSAGE_DAO_THROTTLING, MESSAGE_DAO_INVALID_REQUEST
}

class MessageDaoThrottlingException extends MessageDaoException {
	private static final long serialVersionUID = -5665995036030445803L;

	public MessageDaoThrottlingException(MessageDaoErrorCode errorCode) {
		super(429, errorCode);
	}
}

class MessageDaoInvalidRequestException extends MessageDaoException {
	private static final long serialVersionUID = -4454419913510374429L;

	public MessageDaoInvalidRequestException(MessageDaoErrorCode errorCode) {
		super(400, errorCode);
	}
}