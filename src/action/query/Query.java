package action.query;

import action.Action;
import fileio.ActionInputData;
import utils.Utils;

public abstract class Query extends Action {

    private final Collection collection;
    private final Criteria criteria;
    private final SortType sortType;

    public Query(final ActionInputData actionInputData) {
        super(actionInputData);
        this.collection = Utils.stringToQueryCollection(actionInputData.getObjectType());
        this.criteria = Utils.stringToQueryCriteria(actionInputData.getCriteria());
        this.sortType = Utils.stringToQuerySortType(actionInputData.getSortType());
    }

    public final Collection getCollection() {
        return collection;
    }

    public final Criteria getCriteria() {
        return criteria;
    }

    public final SortType getSortType() {
        return sortType;
    }

    public static class Builder {

        private final ActionInputData actionInputData;

        public Builder(final ActionInputData actionInputData) {
            this.actionInputData = actionInputData;
        }

        /**
         * Determines and executes any type of query received
         * @return UserQuery, VideoQuery, ActorQuery depending on the type of query
         */
        public Query build() {
            return switch (Utils.stringToQueryCollection(actionInputData.getObjectType())) {
                case USERS -> new UserQuery(actionInputData);
                case VIDEOS, MOVIES, SHOWS -> new VideoQuery(actionInputData);
                case ACTORS -> new ActorQuery(actionInputData);
            };
        }

    }

    public enum Collection {
        ACTORS, VIDEOS, USERS, MOVIES, SHOWS
    }

    public enum Criteria {
        AVERAGE, AWARDS, FILTER_DESCRIPTION, FAVORITE, LONGEST, MOST_VIEWED, RATINGS, NUM_RATINGS
    }

    public enum SortType {
        ASC, DESC
    }
}
