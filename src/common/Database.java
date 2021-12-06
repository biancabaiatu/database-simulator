package common;

import actor.Actor;
import entertainment.Movie;
import entertainment.Series;
import entertainment.Video;
import fileio.Input;
import user.User;

import java.util.ArrayList;
import java.util.List;

public final class Database {

    private final List<Actor> actors;
    private final List<Series> series;
    private final List<Movie> movies;
    private final List<User> users;

    private static Database instance = null;

    private Database() {
        this.actors = new ArrayList<>();
        this.series = new ArrayList<>();
        this.movies = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    /**
     * Initializes the database
     * @param input input received from the Input class
     */
    public void initialize(final Input input) {
        this.actors.addAll(input.getActors().stream().map(Actor::new).toList());
        this.series.addAll(input.getSerials().stream().map(Series::new).toList());
        this.movies.addAll(input.getMovies().stream().map(Movie::new).toList());
        this.users.addAll(input.getUsers().stream().map(User::new).toList());
    }

    /**
     * Gets instance of the Database
     * class using the Singleton pattern
     * @return instance of Database class
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Adds a new actor in the actors' list from the Database
     * @param actor to add in the list
     */
    public void addActor(final Actor actor) {
        this.actors.add(actor);
    }

    /**
     * Adds a new Video in the series' or movies' list from the Database
     * @param video to add in the list
     */
    public void addVideo(final Video video) {
        if (video instanceof Movie) {
            this.movies.add((Movie) video);
        } else {
            this.series.add((Series) video);
        }
    }

    /**
     * Adds a new user in the users' list from the Database
     * @param user to add in the list
     */
    public void addUser(final User user) {
        this.users.add(user);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Series> getSeries() {
        return series;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<User> getUsers() {
        return users;
    }

    /**
     * Makes a list formed from all the movies and shows
     * @return List of videos
     */
    public List<Video> getVideos() {
        List<Video> videos = new ArrayList<>();
        videos.addAll(movies);
        videos.addAll(series);
        return videos;
    }

    /**
     * Deletes all data from the Database
     */
    public void clear() {
        this.actors.clear();
        this.movies.clear();
        this.series.clear();
        this.users.clear();
    }

}
