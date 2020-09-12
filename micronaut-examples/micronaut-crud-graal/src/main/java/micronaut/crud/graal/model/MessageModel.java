package micronaut.crud.graal.model;

public class MessageModel {
    private final String id;
    private final String message;

    public MessageModel(String id, String message) {
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
