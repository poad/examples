package org.bitbucket.poad1010.example.springboot.jpa.resource.impl;

import org.bitbucket.poad1010.example.springboot.jpa.entity.MessageEntity;
import org.bitbucket.poad1010.example.springboot.jpa.exception.UncheckedGeneralSecurityException;
import org.bitbucket.poad1010.example.springboot.jpa.model.MessageModel;
import org.bitbucket.poad1010.example.springboot.jpa.repository.MessageRepository;
import org.bitbucket.poad1010.example.springboot.jpa.request.CreateRequest;
import org.bitbucket.poad1010.example.springboot.jpa.request.UpdateRequest;
import org.bitbucket.poad1010.example.springboot.jpa.resource.MessageResource;
import org.bitbucket.poad1010.example.springboot.jpa.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by ken-yo on 2016/08/07.
 */
@Component
public class MessageResourceImpl implements MessageResource {
    @Autowired
    private MessageRepository repository;

    @Override
    public List<MessageResponse> list() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(entity -> MessageModel.of(entity).toResponse()).collect(Collectors.toList());
    }

    @Override
    public MessageResponse get(@NotNull @PathParam("id") String id) {
        Optional<MessageEntity> entity = Optional.ofNullable(repository.findOne(id));
        Optional<MessageResponse> response = entity.map(e -> MessageModel.of(e).toResponse());
        if (response.isPresent()) {
            return response.get();
        }
        throw new NotFoundException();
    }

    @Override
    public MessageResponse create(CreateRequest request) {
        MessageModel model = new MessageModel(null, request.getName(), request.getMessage());
        MessageEntity entity = repository.save(model.toEntity());
        return MessageModel.of(entity).toResponse();
    }

    @Override
    public MessageResponse update(UpdateRequest request) {
        try {
            MessageModel model = new MessageModel(request.getId(), request.getName(), request.getMessage());
            MessageEntity entity = repository.save(model.toEntity());
            return MessageModel.of(entity).toResponse();
        } catch (UncheckedIOException | UncheckedGeneralSecurityException e) {
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void delete(@NotNull @PathParam("id") String id) {
        repository.delete(id);
    }
}
