package micronaut.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "message")
public class MessageEntity {
    @Id
    @NotNull
    private String id;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    public MessageEntity() {
    }

    public MessageEntity(@NotNull String id, @NotNull String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
