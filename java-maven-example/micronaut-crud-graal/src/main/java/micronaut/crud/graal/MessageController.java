package micronaut.crud.graal;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import micronaut.crud.graal.domain.MessageDomain;
import micronaut.crud.graal.model.MessageModel;
import micronaut.crud.graal.model.MessageRequest;
import micronaut.crud.graal.repository.MessageRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@Controller("/message")
public class MessageController {
    protected final MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @Get("/{id}")
    public MessageModel show(String id) {
        return repository
                .findById(id)
                .map(domain -> new MessageModel(domain.getId(), domain.getMessage()))
                .orElse(null);
    }

    @Get("/")
    public List<MessageModel> list() {
        return repository.findAll()
                .stream()
                .map(domain -> new MessageModel(domain.getId(), domain.getMessage()))
                .collect(Collectors.toList());
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Put("/{id}")
    public HttpResponse update(String id, @Body @Valid MessageRequest message) {
        int numberOfEntitiesUpdated = repository.update(id, message.getMessage());

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, URI.create("/message/" + id).getPath());
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Post("/")
    public HttpResponse<MessageModel> save(@Body @Valid MessageRequest message) {
        String id = repository.uuid();
        MessageDomain domain = repository.save(id, message.getMessage());

        return HttpResponse
                .created(new MessageModel(domain.getId(), domain.getMessage()))
                .headers(headers -> headers.location(URI.create("/message/" + id)));
    }

    @Delete("/{id}")
    public HttpResponse delete(@NotBlank String id) {
        repository.deleteById(id);
        return HttpResponse.noContent();
    }

    @Delete("/")
    public HttpResponse deleteAll() {
        repository.deleteAll();
        return HttpResponse.noContent();
    }
}
