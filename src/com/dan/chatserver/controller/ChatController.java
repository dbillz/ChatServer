package com.dan.chatserver.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dan.chatserver.messageDao.MessageDAO;
import com.dan.chatserver.types.ChatException;
import com.dan.chatserver.types.Message;
import com.dan.chatserver.userDao.UserDAO;

//TODO: String validation
//TODO: Add longer term token validation instead of supporting 1 hour bearer tokens alone
public class ChatController {

	private static final String SYSTEM_ALIAS = "SYSTEM";
	private final MessageDAO messageDao;
	private final UserDAO userDao;
	private final Map<String, String> onlineUsers;
	private final String roomPassword;

	public ChatController(MessageDAO messageDao, UserDAO userDao, String roomPassword) {
		this.messageDao = messageDao;
		this.userDao = userDao;
		this.roomPassword = roomPassword;
		onlineUsers = new HashMap<>();
	}

	public String signup(String email, String alias, String passwordHash, String roomPassword) throws ChatException {
		if (!this.roomPassword.equals(roomPassword)) {
			throw new ChatControllerForbiddenException(ChatControllerErrorCode.INVALID_ROOM_PASSWORD);
		}
		return userDao.registerUser(email, alias, passwordHash);
	}

	public String joinServer(String email, String passwordHash) throws ChatException {
		String token = userDao.authenticateUser(email, passwordHash);
		String userId = userDao.getUserId(email);
		String alias = userDao.getUserAlias(userId);
		onlineUsers.put(userId, alias);
		messageDao.sendMessage(new Message(SYSTEM_ALIAS, alias + " has joined the room."));
		return token;
	}

	public void leaveServer(String authToken) throws ChatException {
		String userId = userDao.reauthenticate(authToken);
		String alias = userDao.getUserAlias(userId);
		messageDao.sendMessage(new Message(SYSTEM_ALIAS, alias + " has left the room."));
		onlineUsers.remove(userId);
	}

	public Collection<String> getOnlineUsers() {
		return onlineUsers.values();
	}

	public List<Message> getVisibleMessagesForUser(String authToken) throws ChatException {
		String userId = userDao.reauthenticate(authToken);
		return messageDao.getAllMessages(userId);
	}

	public List<Message> getNewMessagesForUser(String authToken, String lastMessageId) throws ChatException {
		String userId = userDao.reauthenticate(authToken);
		return messageDao.getNewMessages(userId, lastMessageId);
	}

	public void sendMessage(String authToken, String message) throws ChatException {
		String userId = userDao.reauthenticate(authToken);
		String alias = userDao.getUserAlias(userId);
		messageDao.sendMessage(new Message(alias, message));
	}

}
