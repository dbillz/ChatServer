package com.dan.chatserver.messageDao;

import java.util.List;
import java.util.Optional;

import com.dan.chatserver.types.Message;

public interface MessageDAO {

	List<Message> getAllMessages(String userId) throws MessageDaoException;

	List<Message> getNewMessages(String userId, String lastReceivedMessageId) throws MessageDaoException;
	
	void sendMessage(Message message) throws MessageDaoException;
}
