package micronaut.crud.graal.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="message")
@Immutable
public class MessageDomain {
    @Id
    private final String id;

    @NotNull
    @Column
    private final String message;

    public MessageDomain() {
        this(null, null);
    }

    public MessageDomain(@NotBlank String id, @NotBlank String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
