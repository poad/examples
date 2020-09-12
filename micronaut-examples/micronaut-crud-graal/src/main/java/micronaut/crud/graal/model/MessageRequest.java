package micronaut.crud.graal.model;

public class MessageRequest {
    private final String message;

    public MessageRequest() {
        this(null);
    }

    public MessageRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
