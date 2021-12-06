package utils;

import action.Action;
import action.command.Command;
import action.query.Query;
import action.recommendation.Recommendation;
import actor.ActorsAwards;
import common.Constants;
import entertainment.Genre;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * The class contains static methods that helps with parsing.
 *
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    /**
     * Transforms a string into an enum
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        return switch (genre.toLowerCase()) {
            case "action" -> Genre.ACTION;
            case "adventure" -> Genre.ADVENTURE;
            case "drama" -> Genre.DRAMA;
            case "comedy" -> Genre.COMEDY;
            case "crime" -> Genre.CRIME;
            case "romance" -> Genre.ROMANCE;
            case "war" -> Genre.WAR;
            case "history" -> Genre.HISTORY;
            case "thriller" -> Genre.THRILLER;
            case "mystery" -> Genre.MYSTERY;
            case "family" -> Genre.FAMILY;
            case "horror" -> Genre.HORROR;
            case "fantasy" -> Genre.FANTASY;
            case "science fiction" -> Genre.SCIENCE_FICTION;
            case "action & adventure" -> Genre.ACTION_ADVENTURE;
            case "sci-fi & fantasy" -> Genre.SCI_FI_FANTASY;
            case "animation" -> Genre.ANIMATION;
            case "kids" -> Genre.KIDS;
            case "western" -> Genre.WESTERN;
            case "tv movie" -> Genre.TV_MOVIE;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        return switch (award) {
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            default -> null;
        };
    }

    /**
     * Transforms an array of JSON's into an array of strings
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwardsa as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }

    /**
     * Transforms a string into a command type
     * @param type of command
     * @return a command type
     */
    public static Command.Type stringToCommandType(final String type) {
        return switch (type.toLowerCase()) {
            case "favorite" -> Command.Type.FAVOURITE;
            case "view" -> Command.Type.VIEW;
            case "rating" -> Command.Type.RATING;
            default -> null;
        };
    }

    /**
     * Transforms a string into a query criteria
     * @param criteria of query
     * @return a query criteria
     */
    public static Query.Criteria stringToQueryCriteria(final String criteria) {
        return switch (criteria.toLowerCase()) {
            case "average" -> Query.Criteria.AVERAGE;
            case "awards" -> Query.Criteria.AWARDS;
            case "filter_description" -> Query.Criteria.FILTER_DESCRIPTION;
            case "most_viewed" -> Query.Criteria.MOST_VIEWED;
            case "longest" -> Query.Criteria.LONGEST;
            case "favorite" -> Query.Criteria.FAVORITE;
            case "ratings" -> Query.Criteria.RATINGS;
            case "num_ratings" -> Query.Criteria.NUM_RATINGS;
            default -> null;
        };
    }

    /**
     * Transforms a string into an action type
     * @param type of action
     * @return an action type
     */
    public static Action.Type stringToActionType(final String type) {
        return switch (type.toLowerCase()) {
            case "command" -> Action.Type.COMMAND;
            case "query" -> Action.Type.QUERY;
            case "recommendation" -> Action.Type.RECOMMENDATION;
            default -> null;
        };
    }

    /**
     * Transforms a string into a query collection
     * @param collection of query
     * @return a query collection
     */
    public static Query.Collection stringToQueryCollection(final String collection) {
        return switch (collection.toLowerCase()) {
            case "actors" -> Query.Collection.ACTORS;
            case "videos" -> Query.Collection.VIDEOS;
            case "shows" -> Query.Collection.SHOWS;
            case "movies" -> Query.Collection.MOVIES;
            case "users" -> Query.Collection.USERS;
            default -> null;
        };
    }

    /**
     * Transforms a string into a query sort type
     * @param sortType of query
     * @return a query sort type
     */
    public static Query.SortType stringToQuerySortType(final String sortType) {
        return switch (sortType.toLowerCase()) {
            case "asc" -> Query.SortType.ASC;
            case "desc" -> Query.SortType.DESC;
            default -> null;
        };
    }

    /**
     * Transforms a string into a recommendation type
     * @param type of recommendation
     * @return a recommendation type
     */
    public static Recommendation.Type stringToRecommendationType(final String type) {
        return switch (type.toLowerCase()) {
            case "standard" -> Recommendation.Type.STANDARD;
            case "best_unseen" -> Recommendation.Type.BEST_UNSEEN;
            case "popular" -> Recommendation.Type.POPULAR;
            case "favorite" -> Recommendation.Type.FAVORITE;
            case "search" -> Recommendation.Type.SEARCH;
            default -> null;
        };
    }

    /**
     * Transforms a string into an user type
     * @param subscription of the user
     * @return an user type
     */
    public static User.Type stringToSubscriptionType(final String subscription) {
        return switch (subscription.toLowerCase()) {
            case "basic" -> User.Type.BASIC;
            case "premium" -> User.Type.PREMIUM;
            default -> null;
        };
    }
}
