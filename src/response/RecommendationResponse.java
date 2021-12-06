package response;

import action.recommendation.Recommendation;

public class RecommendationResponse<T> extends Response {

    public RecommendationResponse(
            final int id,
            final Recommendation.Type type,
            final T payload) {
        super(id);
        setMessage(generateMessage(type, payload));
    }

    private String generateMessage(final Recommendation.Type type, final T payload) {
        if (payload == null) {
            return type.getType() + " cannot be applied!";
        } else {
            return type.getType() + " result: " + payload;
        }

    }
}
