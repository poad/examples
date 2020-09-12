package micronaut.crud.graal.repository;

import micronaut.crud.graal.domain.MessageDomain;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    String uuid();

    Optional<MessageDomain> findById(@NotBlank String id);

    MessageDomain save(@NotBlank String id, @NotBlank String message);

    void deleteById(@NotBlank String id);

    void deleteAll();

    List<MessageDomain> findAll();

    int update(@NotBlank String id, @NotBlank String message);
}
