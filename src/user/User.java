package user;

import common.exception.AlreadyFavouriteException;
import common.exception.VideoNotWatchedException;
import entertainment.Video;
import fileio.UserInputData;
import utils.Utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class User {

    private final String username;
    private final Type subscription;
    private final Map<String, Integer> history;
    private final Set<String> favourites;

    private int numberOfRatings;

    public User(final UserInputData inputData) {

        this.history = inputData.getHistory();
        this.username = inputData.getUsername();
        this.subscription = Utils.stringToSubscriptionType(inputData.getSubscriptionType());
        this.numberOfRatings = 0;

        this.favourites = new HashSet<>();
        this.favourites.addAll(inputData.getFavoriteMovies());
    }

    public enum Type {
        BASIC, PREMIUM
    }

    public String getUsername() {
        return username;
    }

    public Type getSubscription() {
        return subscription;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public Set<String> getFavourites() {
        return favourites;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    /**
     * Adds a certain video in the favourites' list of an user
     * @param video to be added in the list
     * @throws AlreadyFavouriteException thrown when the video
     * was previously added in the user's favourites' list
     * @throws VideoNotWatchedException thrown when the video
     * was not watched by the user
     */
    public void addFavourite(final Video video)
            throws AlreadyFavouriteException, VideoNotWatchedException {
        if (!this.history.containsKey(video.getTitle())) {
            throw new VideoNotWatchedException(video.getTitle());
        }
        if (!this.favourites.add(video.getTitle())) {
            throw new AlreadyFavouriteException(video.getTitle());
        }
    }

    /**
     * Adds a video in the history list of an user
     * and increases the number of views
     * @param video Video to be added in the list
     */
    public void viewVideo(final Video video) {
        if (this.history.containsKey(video.getTitle())) {
            this.history.put(video.getTitle(), this.history.get(video.getTitle()) + 1);
        } else {
            this.history.put(video.getTitle(), 1);
        }
    }

    /**
     * Increases the number of times a certain user
     * has given ratings to videos
     * @param video to be searched
     */
    public void rateVideo(final Video video) {
        this.numberOfRatings++;
    }

    /**
     * Searches in the history of an user and determines
     * whether the user has watched a video
     * @param video to be searched in the history
     * @return Boolean value
     */
    public boolean hasWatched(final Video video) {
        return this.history.containsKey(video.getTitle());
    }

    @Override
    public String toString() {
        return username;
    }
}
