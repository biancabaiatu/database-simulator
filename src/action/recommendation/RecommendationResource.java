package action.recommendation;

import common.Database;
import common.exception.NotPremiumUserException;
import entertainment.Genre;
import entertainment.Video;
import entertainment.VideoResource;
import user.User;

import java.util.LinkedHashMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

public final class RecommendationResource {

    private final Database database;

    private static RecommendationResource instance = null;

    private RecommendationResource() {
        this.database = Database.getInstance();
    }

    /**
     * Gets instance of the RecommendationResource
     * class using the Singleton pattern
     * @return instance of RecommendationResource class
     */
    public static RecommendationResource getInstance() {
        if (instance == null) {
            instance = new RecommendationResource();
        }
        return instance;
    }

    /**
     * Finds the first video in the database
     * that was not watched by the user
     * @param user to be searched
     * @return Video
     */
    public Video getStandard(final User user) {
        Optional<Video> result =
                database.getVideos().stream()
                        .filter((a) -> (!user.hasWatched(a)))
                        .findFirst();
        return result.orElse(null);
    }

    /**
     * Finds the first video in the database
     * that was not watched by the user and has the highest rating
     * @param user to be searched
     * @return Video
     */
    public Video getBestUnseen(final User user) {
        Optional<Video> result =
                database.getVideos().stream()
                        .filter((a) -> (!user.hasWatched(a)))
                        .max(Comparator.comparingDouble(Video::getRating));
        return result.orElse(null);
    }

    /**
     * Finds the first video in the database
     * that was not watched by the user and has the best rated genre
     * @param user to be searched
     * @return Video
     */
    public Video getMostPopular(final User user) throws NotPremiumUserException {
        if (!(user.getSubscription() == User.Type.PREMIUM)) {
            throw new NotPremiumUserException(user);
        }

        Map<Genre, Integer> genreViews = new HashMap<>();
        for (Genre genre : Genre.values()) {
            genreViews.put(
                    genre,
                    VideoResource.getInstance().getVideosByGenre(genre).stream()
                            .map(VideoResource.getInstance()::getVideoViews)
                            .reduce(0, Integer::sum));
        }

        Map<Genre, Integer> sortedGenreViews = new LinkedHashMap<>();
        genreViews.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedGenreViews.put(x.getKey(), x.getValue()));

        for (Map.Entry<Genre, Integer> entry : sortedGenreViews.entrySet()) {

            Optional<Video> result =
                    VideoResource.getInstance()
                        .getVideosByGenre(entry.getKey())
                        .stream()
                        .filter((a) -> (!user.hasWatched(a)))
                        .findFirst();
            if (result.isPresent()) {
                return result.get();
            }
        }

        return null;
    }

    /**
     * Finds the first video in the database
     * that was not watched by the user
     * and was added the most in the users' favourites' list
     * @param user to be searched
     * @return Video
     */
    public Video getMostlyLikedVideo(final User user) throws NotPremiumUserException {
        if (!(user.getSubscription() == User.Type.PREMIUM)) {
            throw new NotPremiumUserException(user);
        }

        Optional<Video> result = database.getVideos().stream()
                .filter((a) -> (!user.hasWatched(a)))
                .max(Comparator.comparing(VideoResource.getInstance()::getVideoFavouriteCount));

        return result.orElse(null);
    }

    /**
     * Finds a list of videos in the database
     * that were not watched by the user and have a given genre
     * @param user to be searched
     * @param genre to be searched
     * @return List of videos
     */
    public List<Video> search(final User user, final Genre genre) throws NotPremiumUserException {
        if (!(user.getSubscription() == User.Type.PREMIUM)) {
            throw new NotPremiumUserException(user);
        }

        return database.getVideos().stream()
                .filter((a) -> (!user.hasWatched(a) && a.getGenres().contains(genre)))
                .sorted(Comparator.comparingDouble(Video::getRating)
                                .thenComparing(Video::getTitle))
                .collect(Collectors.toList());
    }

}
