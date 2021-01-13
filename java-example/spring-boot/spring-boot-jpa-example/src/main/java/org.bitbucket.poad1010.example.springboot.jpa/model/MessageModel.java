package org.bitbucket.poad1010.example.springboot.jpa.model;

import org.bitbucket.poad1010.example.springboot.jpa.entity.MessageEntity;
import org.bitbucket.poad1010.example.springboot.jpa.response.MessageResponse;

import java.util.Optional;

/**
 * Created by ken-yo on 2016/08/06.
 */
public class MessageModel implements Model<MessageEntity, MessageResponse> {
    private String id;
    private String name;
    private String message;

    public MessageModel(String id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public static MessageModel of(MessageEntity entity) {
        String id = entity.getId();
        String name = entity.getName();
        String message = entity.getMessage();
        return new MessageModel(id, name, message);
    }

    public static MessageModel of(MessageResponse response) {
        String id = response.getId();
        String name = response.getName();
        String message = response.getMessage();
        return new MessageModel(id, name, message);
    }

    @Override
    public MessageEntity toEntity() {
        MessageEntity entity = new MessageEntity();
        entity.setName(name);
        entity.setMessage(message);
        id = Optional.ofNullable(id).orElse(entity.generateID());
        entity.setId(id);
        return entity;
    }

    @Override
    public MessageResponse toResponse() {
        MessageResponse response = new MessageResponse();
        response.setId(id);
        response.setName(name);
        response.setMessage(message);
        return response;
    }
}
