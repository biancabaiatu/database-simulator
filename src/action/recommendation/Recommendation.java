package action.recommendation;

import action.Action;
import common.exception.VideoDBsException;
import entertainment.Video;
import response.RecommendationResponse;
import response.Response;
import entertainment.Genre;
import fileio.ActionInputData;
import user.User;
import user.UserResource;
import utils.Utils;

import java.util.List;

public final class Recommendation extends Action {

    private final User user;
    private final Type type;

    public Recommendation(final ActionInputData actionInputData) {
        super(actionInputData);
        this.type = Utils.stringToRecommendationType(actionInputData.getType());
        this.user =
                UserResource.getInstance()
                        .getUserByUsername(getActionInputData().getUsername());
    }

    /**
     * Executes any kind of recommendation query on the database
     * @return Response to given recommendation type
     */
    public Response execute() {
        return switch (type) {
            case STANDARD -> getStandard();
            case BEST_UNSEEN -> getBestUnseen();
            case FAVORITE -> getMostlyLikedVideo();
            case POPULAR -> getMostPopular();
            case SEARCH -> search();
        };
    }

    /**
     * Gives a response when trying to execute the StandardRecommendation
     * @return Response to StandardRecommendation
     */
    public Response getStandard() {
        return new RecommendationResponse<>(
                getActionInputData().getActionId(),
                Type.STANDARD,
                RecommendationResource.getInstance().getStandard(user));
    }

    /**
     * Gives a response when trying to execute the BestUnseenRecommendation
     * @return Response to BestUnseenRecommendation
     */
    public Response getBestUnseen() {
        return new RecommendationResponse<>(
                getActionInputData().getActionId(),
                Type.BEST_UNSEEN,
                RecommendationResource.getInstance().getBestUnseen(user));
    }

    /**
     * Gives a response when trying to execute the FavoriteRecommendation
     * @return Response to FavoriteRecommendation
     */
    public Response getMostlyLikedVideo() {
        try {
            return new RecommendationResponse<>(
                    getActionInputData().getActionId(),
                    Type.FAVORITE,
                    RecommendationResource.getInstance().getMostlyLikedVideo(user));
        } catch (VideoDBsException e) {
            return new RecommendationResponse<>(
                    getActionInputData().getActionId(),
                    Type.FAVORITE,
                    null);
        }
    }

    /**
     * Gives a response when trying to execute the PopularRecommendation
     * @return Response to PopularRecommendation
     */
    public Response getMostPopular() {
        try {
            return new RecommendationResponse<>(
                    getActionInputData().getActionId(),
                    Type.POPULAR,
                    RecommendationResource.getInstance().getMostPopular(user));
        } catch (VideoDBsException e) {
            return new RecommendationResponse<>(
                    getActionInputData().getActionId(),
                    Type.POPULAR,
                    null);
        }
    }

    /**
     * Gives a response when trying to execute the SearchRecommendation
     * @return Response to SearchRecommendation
     */
    public Response search() {
        Genre genre = Utils.stringToGenre(getActionInputData().getGenre());

        try {
            List<Video> result = RecommendationResource.getInstance().search(user, genre);
            if (result.size() == 0) {
                result = null;
            }

            return new RecommendationResponse<>(
                    getActionInputData().getActionId(),
                    Type.SEARCH,
                    result);
        } catch (VideoDBsException e) {
            return new RecommendationResponse<>(
                    getActionInputData().getActionId(),
                    Type.SEARCH,
                    null);
        }
    }

    public enum Type {
        STANDARD("StandardRecommendation"),
        BEST_UNSEEN("BestRatedUnseenRecommendation"),
        POPULAR("PopularRecommendation"),
        FAVORITE("FavoriteRecommendation"),
        SEARCH("SearchRecommendation");

        private final String type;

        Type(final String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
