package com.github.poad;

import com.github.poad.model.Message;
import com.github.poad.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class MessageResource {

    private static class CreateUpdateRequest {
        private final String message;

        CreateUpdateRequest(String message) {
            this.message = message;
        }
    }

    private final MessageService service;

    MessageResource(MessageService service) {
        this.service = service;
    }

    @GetMapping
    public List<Message> list() {
        return service.list();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Message create(CreateUpdateRequest request) {
        return service.createMassage(request.message);
    }

    @PutMapping(value = "/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public Message update(
            @PathVariable("id") String id,
            CreateUpdateRequest request) {
        return service.updateMessage(id, request.message);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        service.deleteMessage(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        service.deleteMessages();
    }

}