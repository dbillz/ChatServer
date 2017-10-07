package com.dan.chatserver.userDao;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserDaoInMemoryImpl implements UserDAO {

	private final Map<String, String> knownAliases;
	private final Map<String, String> passwordMap; 
	private final Map<String, AuthToken> knownTokens; //Map of token string to token
	private final Map<String, String> emailMap;

	public UserDaoInMemoryImpl() {
		knownAliases = new HashMap<>();
		passwordMap = new HashMap<>();
		knownTokens = new HashMap<>();
		emailMap = new HashMap<>();
	}

	@Override
	public String registerUser(String email, String alias, String passwordHash) throws UserDaoException {
		if (emailMap.containsKey(email)) {
			throw new UserDaoConflictException(UserDaoErrorCode.USER_DAO_INVALID_USER_CREATION);
		}
		String userId = UUID.randomUUID().toString();
		emailMap.put(email, userId);
		passwordMap.put(userId, passwordHash);
		knownAliases.put(userId, alias);
		return userId;
	}

	@Override
	public String authenticateUser(String email, String passwordHash) throws UserDaoException {
		String userId = getUserId(email);

		if (passwordMap.containsKey(userId) && passwordMap.get(userId).equals(passwordHash)) {
			return registerNewAuthToken(userId);
		} else {
			throw new UserDaoUnauthenticatedException(UserDaoErrorCode.USER_DAO_INVALID_LOGIN);
		}
	}

	@Override
	public String reauthenticate(String token) throws UserDaoException {
		if (knownTokens.containsKey(token)) {
			final AuthToken knownToken = knownTokens.get(token);
			if (knownToken.isExpired()) {
				throw new UserDaoUnauthenticatedException(UserDaoErrorCode.USER_DAO_INVALID_TOKEN);
			} else {
				return knownToken.getOwnerId();
			}
		} else {
			throw new UserDaoUnauthenticatedException(UserDaoErrorCode.USER_DAO_INVALID_LOGIN);
		}
	}

	@Override
	public String getUserAlias(String userId) throws UserDaoException {
		if (!knownAliases.containsKey(userId)) {
			throw new UserDaoResourceNotFoundException(UserDaoErrorCode.USER_DAO_USER_NOT_FOUND);
		}
		return knownAliases.get(userId);
	}

	@Override
	public String getUserId(String email) throws UserDaoException {
		if (!emailMap.containsKey(email)) {
			throw new UserDaoUnauthenticatedException(UserDaoErrorCode.USER_DAO_INVALID_LOGIN);
		}
		return emailMap.get(email);
	}

	private String registerNewAuthToken(String userId) {
		final AuthToken newToken = new AuthToken(userId);
		String tokenString = newToken.getToken();
		knownTokens.put(tokenString, newToken);
		return tokenString;
	}
}

class AuthToken {
	private static final long SECONDS_TO_EXPIRY = 1 * 60 * 60; // 1 hour
	private final String token;
	private final Instant timestamp;
	private final String ownerId;

	public AuthToken(String ownerId) {
		token = UUID.randomUUID().toString();
		timestamp = Instant.now();
		this.ownerId = ownerId;
	}

	public String getToken() {
		return token;
	}

	public boolean matchesToken(String token) {
		return this.token.equals(token);
	}

	public String getOwnerId() {
		return ownerId;
	}

	public boolean isExpired() {
		return timestamp.plusSeconds(SECONDS_TO_EXPIRY).isBefore(Instant.now());
	}
}
