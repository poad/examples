package com.github.poad.example.database.jdbi.entity;

public class Message {
	private Long id;
	private String message;


	public Message() {
	}


	public Message(Long id, String message) {
		this.id = id;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
