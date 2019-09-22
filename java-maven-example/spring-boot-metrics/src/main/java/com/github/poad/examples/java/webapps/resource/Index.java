package com.github.poad.examples.java.webapps.resource;

import com.github.poad.examples.java.webapps.domain.Message;
import com.github.poad.examples.java.webapps.entity.MessageEntity;
import com.github.poad.examples.java.webapps.repository.MessageRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class Index {

    private final MessageRepository repository;

    public Index(MessageRepository repository) {
        this.repository = repository;
    }

    @GET
    public List<Message> list() {
        return repository.findAll().stream()
                .map(entity -> {
                    Message response = new Message();
                    response.setId(entity.getId());
                    response.setMessage(entity.getMessage());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Message byId(@PathParam("id") @NotBlank String id) {
        return repository.findById(id)
                .map(entity -> {
                    Message response = new Message();
                    response.setId(entity.getId());
                    response.setMessage(entity.getMessage());
                    return response;
                })
                .orElseThrow(() -> new WebApplicationException(404));
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Transactional
    public Message create(@NotNull Message message) {
        String id = repository.uuid();
        MessageEntity entity = new MessageEntity(id, message.getMessage());
        repository.saveAndFlush(entity);

        Message response = new Message();
        response.setId(entity.getId());
        response.setMessage(entity.getMessage());
        return response;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    @Path("/{id}")
    public Message update(@PathParam("id") @NotBlank String id, @NotNull Message message) {
        MessageEntity current = repository.findById(id)
                .orElseThrow(() -> new WebApplicationException(404));

        MessageEntity entity = new MessageEntity(current.getId(), message.getMessage());
        repository.saveAndFlush(entity);

        Message response = new Message();
        response.setId(entity.getId());
        response.setMessage(entity.getMessage());
        return response;
    }

    @DELETE
    @Path("/{id}")
    public Message deleteById(@PathParam("id") @NotBlank String id) {
        MessageEntity entity = repository.findById(id)
                .orElseThrow(() -> new WebApplicationException(404));
        repository.delete(entity);

        Message response = new Message();
        response.setId(entity.getId());
        response.setMessage(entity.getMessage());
        return response;

    }
}
