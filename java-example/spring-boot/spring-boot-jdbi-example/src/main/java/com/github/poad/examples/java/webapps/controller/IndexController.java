package com.github.poad.examples.java.webapps.controller;

import com.github.poad.examples.java.webapps.RestAppException;
import com.github.poad.examples.java.webapps.database.MessageBean;
import com.github.poad.examples.java.webapps.database.MessageRepository;
import com.github.poad.examples.java.webapps.interfaces.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Immutable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Immutable
@Controller
@Validated
@RequestMapping("/message")
public class IndexController {

    private final MessageRepository repository;

    @Autowired
    public IndexController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/")
    public List<Message> message() {
        return repository.list()
                .stream()
                .map(message -> {
                    Message response = new Message();
                    response.setId(message.getId());
                    response.setMessage(message.getMessage());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public Message get(@NotNull String id) {
        return repository.get(id).map(bean -> {
            Message response = new Message();
            response.setId(bean.getId());
            response.setMessage(bean.getMessage());
            return response;
        }).orElseThrow(() -> new RestAppException(404));
    }

    @PostMapping(consumes= MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public String create(Message message) {
        MessageBean bean = new MessageBean();
        bean.setMessage(message.getMessage());
        return repository.create(bean);
    }

    @PutMapping(path = "/{id}")
    public boolean update(String id, Message message) {
        MessageBean bean = new MessageBean();
        bean.setId(id);
        bean.setMessage(message.getMessage());
        return repository.update(bean);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@NotNull String id) {
        repository.delete(id);
    }
}
