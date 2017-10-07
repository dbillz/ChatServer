package com.dan.chatserver.userDao;

public interface UserDAO {

	/**
	 * Authenticates a known user to the system and returns a token used for reauthenticateUser.
	 * @param email
	 * @param passwordHash
	 * @return auth token
	 * @throws UserDaoException
	 */
	String authenticateUser(String email, String passwordHash) throws UserDaoException;
	
	/**
	 * Returns true if the provided token is valid at this time.
	 * @param token
	 * @return userId
	 * @throws UserDaoException
	 */
	String reauthenticate(String token) throws UserDaoException;
	
	/**
	 * Adds a new known user to the system.
	 * @param email
	 * @param passwordHash
	 * @return userId
	 * @throws UserDaoException
	 */
	String registerUser(String email, String alias, String passwordHash) throws UserDaoException;
	
	/**
	 * Returns the alias of this user.
	 * @param userId
	 * @return alias
	 * @throws UserDaoException
	 */
	String getUserAlias(String userId) throws UserDaoException;

	/**
	 * Returns the id of the user with this email.
	 * @param email
	 * @return userId
	 * @throws UserDaoException
	 */
	String getUserId(String email) throws UserDaoException;

	
}
