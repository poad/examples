package com.github.poad.examples.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="message")
@Entity
@NamedQueries({
	  @NamedQuery(name = "Message.list", 
	     query = "Select m from Message m"),
	  @NamedQuery(name = "Message.byId", 
	     query = "Select m from Message m where m.id = :id")
	})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    private Long id;
 
    private String message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
