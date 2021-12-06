package entertainment;

import fileio.MovieInputData;
import common.exception.AlreadyRatedByUserException;
import user.User;
import common.exception.VideoNotWatchedException;

import java.util.HashMap;
import java.util.Map;

public final class Movie extends Video {

    private final int duration;
    private final Map<User, Double> ratings;

    public Movie(final MovieInputData inputData) {
        super(inputData);
        this.duration = inputData.getDuration();
        this.ratings = new HashMap<>();
    }

    public int getDuration() {
        return duration;
    }

    /**
     * Calculates the rating of a movie
     * @return Double that represents the rating
     */
    public double getRating() {
        double rating = 0;
        for (Map.Entry<User, Double> entry : ratings.entrySet()) {
            rating += entry.getValue();
        }
        return rating / ratings.size();
    }

    /**
     * Adds a new rating for a movie given by an user
     * @param user gives the rating
     * @param rating to be added
     * @throws AlreadyRatedByUserException thrown when the movie
     * was already rated by that user
     * @throws VideoNotWatchedException thrown when the movie
     * was not watched by that user
     */
    public void addRating(final User user, final double rating)
            throws AlreadyRatedByUserException, VideoNotWatchedException {
        if (!user.hasWatched(this)) {
            throw new VideoNotWatchedException(this.getTitle());
        }
        if (this.ratings.containsKey(user)) {
            throw new AlreadyRatedByUserException(this.getTitle());
        }
        this.ratings.put(user, rating);
        user.rateVideo(this);
    }

}
