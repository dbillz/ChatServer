package com.dan.chatserver.demo;

import java.util.List;
import java.util.UUID;

import com.dan.chatserver.controller.ChatController;
import com.dan.chatserver.messageDao.MessageDAO;
import com.dan.chatserver.messageDao.MessageDaoInMemoryImpl;
import com.dan.chatserver.types.ChatException;
import com.dan.chatserver.types.Message;
import com.dan.chatserver.userDao.UserDAO;
import com.dan.chatserver.userDao.UserDaoInMemoryImpl;

public class Runme {

	public static void main(String[] args) throws ChatException, InterruptedException {
		int maxMessages = 50;
		MessageDAO messageDao = new MessageDaoInMemoryImpl(maxMessages);
		UserDAO userDao = new UserDaoInMemoryImpl();
		String roomPassword = "Donkey Kong";
		ChatController controller = new ChatController(messageDao, userDao, roomPassword);

		String alias = "Dan";
		String email = "dgb@amazon.com";
		String password = UUID.randomUUID().toString();
		controller.signup(email, alias, password, roomPassword);
		String token = controller.joinServer(email, password);

		String alias2 = "Hawaiian";
		String email2 = "hawaiian@gmail.com";
		String password2 = UUID.randomUUID().toString();
		controller.signup(email2, alias2, password2, roomPassword);
		String token2 = controller.joinServer(email2, password2);

		controller.sendMessage(token, "Hello World.");
		controller.sendMessage(token2, "Aloha!");
		controller.leaveServer(token2);
		controller.sendMessage(token, "How's everybody doing?");

		System.out.println("Online Users:");
		System.out.println(controller.getOnlineUsers());

		List<Message> messages = controller.getVisibleMessagesForUser(token);
		for (Message m : messages) {
			System.out.println(m);
		}
	}
}
