package response;

import org.json.JSONObject;

public class Response {

    private final int id;
    private String message;

    public Response(final int id) {
        this(id, "");
    }

    public Response(final int id, final String message) {
        this.id = id;
        this.message = message;
    }

    /**
     * Creates a JSON object
     * based on the actions that were made on the database
     * @return JSON object
     */
    public final JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("id", getId());
        object.put("message", getMessage());
        return object;
    }

    public final void setMessage(final String message) {
        this.message = message;
    }

    public final int getId() {
        return id;
    }

    public final String getMessage() {
        return message;
    }
}

