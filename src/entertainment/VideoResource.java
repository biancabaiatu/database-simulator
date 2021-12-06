package entertainment;

import common.Database;
import common.Filter;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class VideoResource {

    private final Database database;

    private static VideoResource instance = null;

    private VideoResource() {
        this.database = Database.getInstance();
    }

    /**
     * Gets instance of the VideoResource
     * class using the Singleton pattern
     * @return instance of VideoResource class
     */
    public static VideoResource getInstance() {
        if (instance == null) {
            instance = new VideoResource();
        }
        return instance;
    }

    /**
     * Finds a list of n videos with the highest rating
     * @param n Number of videos in the list
     * @return List of videos
     */
    public List<Video> getTopRatedVideos(final int n,
                                         final Filter filter,
                                         final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingDouble(Video::getRating)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getVideos().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .filter((video -> (video.getRating() > 0)))
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n movies with the highest rating
     * @param n Number of movies in the list
     * @return List of videos
     */
    public List<Video> getTopRatedMovies(final int n,
                                         final Filter filter,
                                         final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingDouble(Video::getRating)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getMovies().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .filter((video -> (video.getRating() > 0)))
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n shows with the highest rating
     * @param n Number of shows in the list
     * @return List of videos
     */
    public List<Video> getTopRatedShows(final int n,
                                        final Filter filter,
                                        final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingDouble(Video::getRating)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getSeries().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .filter((video -> (video.getRating() > 0)))
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n videos that were added
     * in the favourites' list of the users the most times
     * @param n Number of videos in the list
     * @return List of videos
     */
    public List<Video> getTopFavouriteVideos(final int n,
                                             final Filter filter,
                                             final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingInt(this::getVideoFavouriteCount)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getVideos().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .filter(video -> getVideoFavouriteCount(video) > 0)
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n movies that were added
     * in the favourites' list of the users the most times
     * @param n Number of movies in the list
     * @return List of videos
     */
    public List<Video> getTopFavouriteMovies(final int n,
                                             final Filter filter,
                                             final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingInt(this::getVideoFavouriteCount)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getMovies().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .filter(video -> getVideoFavouriteCount(video) > 0)
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n shows that were added
     * in the favourites' list of the users the most times
     * @param n Number of shows in the list
     * @return List of videos
     */
    public List<Video> getTopFavouriteShows(final int n,
                                            final Filter filter,
                                            final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingInt(this::getVideoFavouriteCount)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getSeries().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .filter(video -> getVideoFavouriteCount(video) > 0)
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n videos that were viewed the most by the users
     * @param n Number of videos in the list
     * @return List of videos
     */
    public List<Video> getTopViewedVideos(final int n,
                                          final Filter filter,
                                          final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingInt(this::getVideoViews)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getVideos().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .filter(video -> getVideoViews(video) > 0)
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n movies that were viewed the most by the users
     * @param n Number of movies in the list
     * @return List of videos
     */
    public List<Video> getTopViewedMovies(final int n,
                                          final Filter filter,
                                          final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingInt(this::getVideoViews)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getMovies().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .filter(video -> getVideoViews(video) > 0)
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n shows that were viewed the most by the users
     * @param n Number of shows in the list
     * @return List of videos
     */
    public List<Video> getTopViewedShows(final int n,
                                         final Filter filter,
                                         final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingInt(this::getVideoViews)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getSeries().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .filter(video -> getVideoViews(video) > 0)
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n videos that have the longest duration
     * @param n Number of videos in the list
     * @return List of videos
     */
    public List<Video> getLongestVideos(final int n,
                                        final Filter filter,
                                        final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingInt(Video::getDuration)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getVideos().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n movies that have the longest duration
     * @param n Number of movies in the list
     * @return List of movies
     */
    public List<Video> getLongestMovies(final int n,
                                        final Filter filter,
                                        final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingInt(Video::getDuration)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getMovies().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of n shows that have the longest duration
     * @param n Number of shows in the list
     * @return List of shows
     */
    public List<Video> getLongestShows(final int n,
                                       final Filter filter,
                                       final boolean reversed) {
        Comparator<Video> comparator = Comparator
                .comparingInt(Video::getDuration)
                .thenComparing(Video::getTitle);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getSeries().stream()
                .filter(video -> isVideoFiltered(video, filter))
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of videos that have a given genre
     * @param genre to be searched
     * @return List of videos
     */
    public List<Video> getVideosByGenre(final Genre genre) {
        return database.getVideos().stream()
                .filter((a) -> (a.getGenres().contains(genre)))
                .collect(Collectors.toList());
    }

    /**
     * Finds a video in the database by its name
     * @param videoName to be searched
     * @return Movie or Show
     */
    public Optional<? extends Video> getVideoByName(final String videoName) {
        Optional<Movie> movie = getMovieByName(videoName);
        if (movie.isPresent()) {
            return movie;
        }
        return getSeriesByName(videoName);
    }

    /**
     * Searches in the database for a movie given its name
     * @param movieName to be searched
     * @return Movie or empty string
     */
    public Optional<Movie> getMovieByName(final String movieName) {
        for (Movie movie : database.getMovies()) {
            if (movie.getTitle().equals(movieName)) {
                return Optional.of(movie);
            }
        }
        return Optional.empty();
    }

    /**
     * Searches in the database for a show given its name
     * @param seriesName to be searched
     * @return Series or empty string
     */
    public Optional<Series> getSeriesByName(final String seriesName) {
        for (Series show : database.getSeries()) {
            if (show.getTitle().equals(seriesName)) {
                return Optional.of(show);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds a list of videos in the database by their names
     * @param videoNames to be searched
     * @return List of videos
     */
    public List<Video> getVideos(final List<String> videoNames) {
        List<Video> videos = new ArrayList<>();
        for (String videoName : videoNames) {
            Optional<? extends Video> result  = getVideoByName(videoName);
            result.ifPresent(videos::add);
        }
        return videos;
    }

    /**
     * Determines the number of times a
     * video was added as favourite by all users
     * @param video to be searched
     * @return Int number that represent the number of times
     * the video was added in the users' favourites' list
     */
    public int getVideoFavouriteCount(final Video video) {
        int count = 0;
        for (User user : database.getUsers()) {
            if (user.getFavourites().contains(video.getTitle())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Determines the number of times a
     * video was viewed by all users
     * @param video to be searched
     * @return Int number that represent the number of times
     * the video was viewed by all users
     */
    public int getVideoViews(final Video video) {
        int views = 0;
        for (User user : database.getUsers()) {
            if (user.getHistory().containsKey(video.getTitle())) {
                views += user.getHistory().get(video.getTitle());
            }
        }
        return views;
    }

    private boolean isVideoFiltered(final Video video, final Filter filter) {
        if (filter.getGenre().isPresent()) {
            if (!video.getGenres().contains(filter.getGenre().get())) {
                return false;
            }
        }
        if (filter.getYear().isPresent()) {
            return filter.getYear().get() == video.getYear();
        }
        return true;
    }
}
