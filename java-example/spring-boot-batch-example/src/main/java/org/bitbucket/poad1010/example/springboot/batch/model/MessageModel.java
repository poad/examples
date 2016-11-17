package org.bitbucket.poad1010.example.springboot.batch.model;


import org.bitbucket.poad1010.example.springboot.batch.entity.MessageEntity;

import java.util.Optional;

/**
 * Created by ken-yo on 2016/08/06.
 */
public class MessageModel implements Model<MessageEntity> {
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

    @Override
    public MessageEntity toEntity() {
        MessageEntity entity = new MessageEntity();
        entity.setName(name);
        entity.setMessage(message);
        id = Optional.ofNullable(id).orElse(entity.generateID());
        entity.setId(id);
        return entity;
    }
}
