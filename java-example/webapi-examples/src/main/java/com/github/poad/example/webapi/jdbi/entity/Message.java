package com.github.poad.example.webapi.jdbi.entity;

public class Message {
	private final Long id;
	private final String message;
	
	public Message(Long id, String message) {
		this.id = id;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}
}
