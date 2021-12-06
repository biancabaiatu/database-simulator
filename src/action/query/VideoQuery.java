package action.query;

import common.Filter;
import response.QueryResponse;
import response.Response;
import entertainment.Video;
import entertainment.VideoResource;
import fileio.ActionInputData;

import java.util.List;

public final class VideoQuery extends Query {

    private final int maxNumberOfVideos;
    private final boolean reversed;
    private final Filter filter;

    public VideoQuery(final ActionInputData actionInputData) {
        super(actionInputData);

        this.maxNumberOfVideos = actionInputData.getNumber();
        this.reversed = getSortType() == SortType.DESC;
        this.filter = new Filter(actionInputData.getFilters());
    }

    /**
     * Function to execute any kind of video related queries on the database
     * @return Response to given query type
     */
    public Response execute() {
        return switch (this.getCriteria()) {
            case FAVORITE -> getTopFavouriteVideos();
            case MOST_VIEWED -> getTopViewedVideos();
            case RATINGS -> getTopRatedVideos();
            case LONGEST -> getLongestVideos();
            default -> null;
        };
    }

    /**
     * Gives a response when trying to execute the FavoriteQuery
     * @return Response to FavoriteQuery
     */
    private Response getTopFavouriteVideos() {
        List<Video> topFavouriteVideos;
        if (getCollection() == Collection.SHOWS) {
            topFavouriteVideos =
                    VideoResource.getInstance()
                            .getTopFavouriteShows(maxNumberOfVideos, filter, reversed);
        } else if (getCollection() == Collection.MOVIES) {
            topFavouriteVideos =
                    VideoResource.getInstance()
                            .getTopFavouriteMovies(maxNumberOfVideos, filter, reversed);
        } else {
            topFavouriteVideos =
                    VideoResource.getInstance()
                            .getTopFavouriteVideos(maxNumberOfVideos, filter, reversed);
        }

        return new QueryResponse<>(
                getActionInputData().getActionId(),
                topFavouriteVideos);
    }

    /**
     * Gives a response when trying to execute the MostViewedQuery
     * @return Response to MostViewedQuery
     */
    private Response getTopViewedVideos() {
        List<Video> topViewedVideo;
        if (getCollection() == Collection.SHOWS) {
            topViewedVideo =
                    VideoResource.getInstance()
                            .getTopViewedShows(maxNumberOfVideos, filter, reversed);
        } else if (getCollection() == Collection.MOVIES) {
            topViewedVideo =
                    VideoResource.getInstance()
                            .getTopViewedMovies(maxNumberOfVideos, filter, reversed);
        } else {
            topViewedVideo =
                    VideoResource.getInstance()
                            .getTopViewedVideos(maxNumberOfVideos, filter, reversed);
        }

        return new QueryResponse<>(
                getActionInputData().getActionId(),
                topViewedVideo);
    }

    /**
     * Gives a response when trying to execute the RatingQuery
     * @return Response to RatingQuery
     */
    private Response getTopRatedVideos() {
        List<Video> topRatedVideos;
        if (getCollection() == Collection.SHOWS) {
            topRatedVideos =
                    VideoResource.getInstance()
                            .getTopRatedShows(maxNumberOfVideos, filter, reversed);
        } else if (getCollection() == Collection.MOVIES) {
            topRatedVideos =
                    VideoResource.getInstance()
                            .getTopRatedMovies(maxNumberOfVideos, filter, reversed);
        } else {
            topRatedVideos =
                    VideoResource.getInstance()
                            .getTopRatedVideos(maxNumberOfVideos, filter, reversed);
        }

        return new QueryResponse<>(
                getActionInputData().getActionId(),
                topRatedVideos);
    }

    /**
     * Gives a response when trying to execute the LongestQuery
     * @return Response to LongestQuery
     */
    private Response getLongestVideos() {
        List<Video> longestVideos;

        if (filter.getInvalid()) {
            return new QueryResponse<>(
                    getActionInputData().getActionId(),
                    "[]");
        }

        if (getCollection() == Collection.SHOWS) {
            longestVideos =
                    VideoResource.getInstance()
                            .getLongestShows(maxNumberOfVideos, filter, reversed);
        } else if (getCollection() == Collection.MOVIES) {
            longestVideos =
                    VideoResource.getInstance()
                            .getLongestMovies(maxNumberOfVideos, filter, reversed);
        } else {
            longestVideos =
                    VideoResource.getInstance()
                            .getLongestVideos(maxNumberOfVideos, filter, reversed);
        }

        return new QueryResponse<>(
                getActionInputData().getActionId(),
                longestVideos);
    }
}
