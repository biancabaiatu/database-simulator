package action.query;

import actor.Actor;
import actor.ActorResource;
import actor.ActorsAwards;
import common.Filter;
import response.QueryResponse;
import response.Response;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ActorQuery extends Query {

    public ActorQuery(final ActionInputData actionInputData) {
        super(actionInputData);
    }

    /**
     * Executes any kind of actor related queries on the database
     * @return Response to given query type
     */
    public Response execute() {
        return switch (this.getCriteria()) {
            case AWARDS -> getActorsByAwards();
            case AVERAGE -> getTopActors();
            case FILTER_DESCRIPTION -> getActorByFilterDescription();
            default -> null;
        };
    }

    /**
     * Gives a response when trying to execute the AverageQuery
     * @return Response to AverageQuery
     */
    private Response getTopActors() {
        final int maxNumberOfActors = getActionInputData().getNumber();
        boolean reversed = getSortType() == SortType.DESC;

        List<Actor> topActors =
                ActorResource.getInstance()
                        .getTopActors(maxNumberOfActors, reversed);

        return new QueryResponse<>(getActionInputData().getActionId(), topActors);
    }
    /**
     * Gives a response when trying to execute the AwardsQuery
     * @return Response to AwardsQuery
     */
    private Response getActorsByAwards() {
        boolean reversed = getSortType() == SortType.DESC;
        Filter filter = new Filter(getActionInputData().getFilters());
        List<ActorsAwards> awards =
                filter.getAwards().isPresent()
                        ? filter.getAwards().get() : new ArrayList<>();

        List<Actor> actors =
                ActorResource.getInstance()
                        .getActorsByAwards(awards, reversed);

        return new QueryResponse<>(getActionInputData().getActionId(), actors);
    }

    /**
     * Gives a response when trying to execute the FilterDescriptionQuery
     * @return Response to FilterDescriptionQuery
     */
    private Response getActorByFilterDescription() {
        Filter filter = new Filter(getActionInputData().getFilters());
        List<String> words =
                filter.getWords().isPresent()
                        ? filter.getWords().get() : new ArrayList<>();

        List<Actor> actors =
                ActorResource.getInstance()
                        .getActorByFilterDescription(words);

        if (getSortType() == SortType.DESC) {
            Collections.reverse(actors);
        }

        return new QueryResponse<>(getActionInputData().getActionId(), actors);
    }

}
