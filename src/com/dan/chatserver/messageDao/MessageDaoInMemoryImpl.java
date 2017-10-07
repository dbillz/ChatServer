package com.dan.chatserver.messageDao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dan.chatserver.types.Message;

public class MessageDaoInMemoryImpl implements MessageDAO {
	
	final Cache<String, Message> messageCache;
	
	public MessageDaoInMemoryImpl(int maxCacheSize){
		messageCache = new Cache<>(maxCacheSize);
	}

	@Override
	public List<Message> getAllMessages(String userId) throws MessageDaoException {
		return messageCache.entrySet().stream()
									.sorted(Map.Entry.comparingByValue())
									.map(entry -> entry.getValue())
									.collect(Collectors.toList());
	}

	@Override
	public List<Message> getNewMessages(String userId, String lastReceivedMessageId) throws MessageDaoException {
		final List<Message> allMessages = getAllMessages(userId);
		
		if (messageCache.containsKey(lastReceivedMessageId)) {
			Message lastReceivedMessage = messageCache.get(lastReceivedMessageId);
			return allMessages.stream().filter(m -> (m.compareTo(lastReceivedMessage) == 1)).collect(Collectors.toList());
		}
		
		return allMessages;
	}

	@Override
	public void sendMessage(Message message) throws MessageDaoException {
		messageCache.put(message.getId(), message);
	}

}

class Cache<K, V> extends LinkedHashMap<K, V> {
	private final int maxCacheSize;
	public Cache(int maxCacheSize) {
		super();
		this.maxCacheSize = maxCacheSize;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry eldest) {
		return size() > maxCacheSize;
	}
}
