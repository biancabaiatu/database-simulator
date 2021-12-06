package entertainment;

import common.exception.AlreadyRatedByUserException;
import common.exception.VideoNotWatchedException;
import fileio.SerialInputData;
import user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.HashSet;


public final class Series extends Video {

    private final List<Season> seasons;
    private final Map<Integer, Set<User>> ratings;

    public Series(final SerialInputData inputData) {
        super(inputData);
        this.seasons = inputData.getSeasons();

        this.ratings = new HashMap<>();
        for (int i = 0; i < seasons.size(); i++) {
            ratings.put(i, new HashSet<>());
        }
    }

    /**
     * Calculates the rating of a show
     * @return Double number that represents the rating of the entire show
     */
    public double getRating() {
        double rating = 0;
        for (Season season : seasons) {
            OptionalDouble seasonRating = season.getRatings()
                    .stream()
                    .mapToDouble(a -> a)
                    .average();
            if (seasonRating.isPresent()) {
                rating += seasonRating.getAsDouble();
            }
        }
        return rating / seasons.size();
    }

    /**
     * Calculates the duration of a show
     * @return Int number that represents the duration of the entire show
     */
    public int getDuration() {
        return seasons.stream().map(Season::getDuration).reduce(0, Integer::sum);
    }

    /**
     * Adds a rating to a season of a show from a given user
     * @param user rates the season
     * @param rating to be added
     * @param seasonNumber The season that is being rated
     * @throws AlreadyRatedByUserException thrown when the season
     * was already rated by the user
     * @throws VideoNotWatchedException thrown when the user has not watched the show
     */
    public void addRating(final User user, final double rating, final int seasonNumber)
            throws AlreadyRatedByUserException, VideoNotWatchedException {
        if (!user.hasWatched(this)) {
            throw new VideoNotWatchedException(this.getTitle());
        }
        if (this.ratings.containsKey(seasonNumber)) {
            Set<User> users = this.ratings.get(seasonNumber);
            if (users.contains(user)) {
                throw new AlreadyRatedByUserException(this.getTitle());
            }
        } else {
            this.ratings.put(seasonNumber, new HashSet<>());
        }
        this.ratings.get(seasonNumber).add(user);
        this.seasons.get(seasonNumber - 1).getRatings().add(rating);
        user.rateVideo(this);
    }
}
