package micronaut.server.controller;

import micronaut.server.model.Message;
import micronaut.server.service.MessageService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@Controller(value = "/message", produces= MediaType.APPLICATION_JSON)
@Validated
public class MessageController {

    @Inject
    private final MessageService service;


    public MessageController(MessageService service) {
        this.service = service;
    }

    @Get
    public List<Message> all() {
        return service.findAll();
    }

    @Get("/{id}")
    public Message byId(String id) {
        return service.findById(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, id));
    }

    @Post
    public String create(@Body @Valid Message message) {
        Message entity = service.save(message.getMessage());
        return entity.getId();
    }

    @Put("/{id}")
    public boolean update(String id, @Body @Valid Message message) {
        return service.update(id, message.getMessage()) == 1;
    }

    @Delete("/{id}")
    public void deleteById(String id) {
        service.deleteById(id);
    }
}
