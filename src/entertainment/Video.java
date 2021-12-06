package entertainment;

import fileio.ShowInput;
import utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Video {

    private final String title;
    private final int year;
    private final List<Genre> genres;
    private final List<String> cast;

    public Video(final ShowInput inputData) {
        this.title = inputData.getTitle();
        this.year = inputData.getYear();
        this.genres = inputData.getGenres().stream()
                .map(Utils::stringToGenre).collect(Collectors.toList());
        this.cast = inputData.getCast();
    }


    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final List<Genre> getGenres() {
        return genres;
    }

    public final List<String> getCast() {
        return cast;
    }

    /**
     * Calculates the rating of a video
     * @return Double number that represents the rating
     */
    public abstract double getRating();

    /**
     * Calculates the duration of a video
     * @return Int number that represents the duration
     */
    public abstract int getDuration();

    @Override
    public final String toString() {
        return title;
    }
}
