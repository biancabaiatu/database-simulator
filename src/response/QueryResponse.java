package response;

public class QueryResponse<T> extends Response {

    public QueryResponse(final int id, final T payload) {
        super(id, "Query result: " + payload);
    }
}
