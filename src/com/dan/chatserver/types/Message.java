package com.dan.chatserver.types;

import java.time.Instant;
import java.util.UUID;

public class Message implements Comparable<Message> {

	private final String sender;
	private final String message;
	private final String id;
	private final Instant timestamp;

	public Message(String sender, String message, String id, Instant timestamp) {
		this.sender = sender;
		this.message = message;
		this.id = id;
		this.timestamp = timestamp;
	}

	public Message(String sender, String message, Instant timestamp) {
		this(sender, message, getNewMessageId(), timestamp);
	}

	public Message(String sender, String message) {
		this(sender, message, getNewMessageId(), Instant.now());
	}

	public String getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}

	public String getId() {
		return id;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "[" + timestamp.toString() + "] " + sender + " : " + message;
	}

	private static String getNewMessageId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public int compareTo(Message otherMessage) {
		if (timestamp.isBefore(otherMessage.getTimestamp())) {
			return -1;
		} else if (timestamp.isAfter(otherMessage.getTimestamp())) {
			return 1;
		} else {
			return 0;
		}
	}

}
