package actor;

import common.Database;
import entertainment.Video;
import entertainment.VideoResource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ActorResource {

    private final Database database;

    private static ActorResource instance = null;

    private ActorResource() {
        this.database = Database.getInstance();
    }

    /**
     * Gets instance of the ActorResource
     * class using the Singleton pattern
     * @return instance of ActorResource class
     */
    public static ActorResource getInstance() {
        if (instance == null) {
            instance = new ActorResource();
        }
        return instance;
    }

    /**
     * Finds a list of n actors sorted by rating
     * @param n number of actors in the list
     * @return List of actors
     */
    public List<Actor> getTopActors(final int n, final boolean reversed) {
        Comparator<Actor> comparator = Comparator
                .comparingDouble(this::getActorRating)
                .thenComparing(Actor::getName);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getActors().stream()
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of actors that have received certain awards
     * @param actorsAwards list of awards
     * @return List of actors
     */
    public List<Actor> getActorsByAwards(final List<ActorsAwards> actorsAwards,
                                         final boolean reversed) {
        List<Actor> actorList = new ArrayList<>();
        for (Actor actor : database.getActors()) {
            if (actor.getAwards().keySet().containsAll(actorsAwards)) {
                actorList.add(actor);
            }
        }

        Comparator<Actor> comparator =
                                Comparator
                                        .comparingInt(this::getAwardsCount)
                                        .thenComparing(Actor::getName);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return actorList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of actors sorted by certain keywords in their career description
     * @param keywords list of keywords
     * @return List of actors
     */
    public List<Actor> getActorByFilterDescription(final List<String> keywords) {
        List<Actor> actorList = new ArrayList<>();
        for (Actor actor : database.getActors()) {
            boolean ok = true;
            String description = actor.getCareerDescription().toLowerCase();
            for (String keyword : keywords) {
                if (!description.contains(' ' + keyword + ' ')) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                actorList.add(actor);
            }
        }

        return actorList.stream()
                .sorted(Comparator.comparing(Actor::getName))
                .collect(Collectors.toList());
    }

    /**
     * Finds the number of awards from a certain actor
     * @param actor to be questioned
     * @return The awards count
     */
    private int getAwardsCount(final Actor actor) {
        return actor.getAwards().values().stream().reduce(0, Integer :: sum);
    }

    /**
     * Function used to calculate the rating of an actor
     * @param actor to be questioned
     * @return Rating of an actor
     */
    private double getActorRating(final Actor actor) {
        double rating = 0;
        int count = 0;
        for (Video video : VideoResource.getInstance().getVideos(actor.getFilmography())) {
            if (video.getRating() != 0) {
                rating += video.getRating();
                count++;
            }
        }
        return rating / count;
    }

    /**
     * Finds an actor by his name
     * @param actorName to find in the database
     * @return The actor found in the database
     */
    public Actor getActorByName(final String actorName) {
        for (Actor actor : database.getActors()) {
            if (actor.getName().equals(actorName)) {
                return actor;
            }
        }
        return null;
    }

}
