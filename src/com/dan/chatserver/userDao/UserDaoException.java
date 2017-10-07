package com.dan.chatserver.userDao;

import com.dan.chatserver.types.ChatException;

public abstract class UserDaoException extends ChatException {
	private static final long serialVersionUID = -989896474834737771L;

	public UserDaoException(int statusCode, UserDaoErrorCode errorCode) {
		super(statusCode, errorCode.name());
	}
}

enum UserDaoErrorCode {
	USER_DAO_INVALID_TOKEN, USER_DAO_INVALID_LOGIN, USER_DAO_INVALID_USER_CREATION,
	USER_DAO_THROTTLING, USER_DAO_USER_NOT_FOUND
}

class UserDaoUnauthenticatedException extends UserDaoException {
	private static final long serialVersionUID = -5844153512565971907L;

	public UserDaoUnauthenticatedException(UserDaoErrorCode errorCode) {
		super(401, errorCode);
	}
}

class UserDaoConflictException extends UserDaoException {
	private static final long serialVersionUID = 6514080608216103823L;

	UserDaoConflictException(UserDaoErrorCode errorCode) {
		super(409, errorCode);
	}
}

class UserDaoThrottlingException extends UserDaoException {
	private static final long serialVersionUID = -1053233055778478345L;

	UserDaoThrottlingException(UserDaoErrorCode errorCode) {
		super(429, errorCode);
	}
}

class UserDaoResourceNotFoundException extends UserDaoException {
	private static final long serialVersionUID = 466162716424714388L;

	public UserDaoResourceNotFoundException(UserDaoErrorCode errorCode) {
		super(404, errorCode);
	}
}