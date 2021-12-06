package response;


public class CommandResponse extends Response {

    public CommandResponse(final int id, final String message, final Type type) {
        super(id);
        setMessage(generateMessage(type, message));
    }

    private String generateMessage(final Type type, final String message) {
        return type == Type.SUCCESS
                ? "success -> " + message : "error -> " + message;
    }

    public enum Type {
        SUCCESS, ERROR
    }
}
