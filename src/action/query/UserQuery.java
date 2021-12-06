package action.query;

import response.QueryResponse;
import response.Response;
import fileio.ActionInputData;
import user.User;
import user.UserResource;

import java.util.List;

public final class UserQuery extends Query {

    public UserQuery(final ActionInputData actionInputData) {
        super(actionInputData);
    }

    /**
     * Executes any kind of user related queries on the database
     * @return Response to given query type
     */
    public Response execute() {
        return switch (this.getCriteria()) {
            case NUM_RATINGS -> getMostActiveUsers();
            default -> null;
        };
    }

    /**
     * Gives a response when trying to execute the NumberOfRatingsQuery
     * @return Response to NumberOfRatingsQuery
     */
    private Response getMostActiveUsers() {
        int maxNumberOfUsers = getActionInputData().getNumber();
        boolean reversed = getSortType() == SortType.DESC;
        List<User> mostActiveUsers =
                UserResource.getInstance()
                        .getMostActiveUsers(maxNumberOfUsers, reversed);

        return new QueryResponse<List<User>>(getActionInputData().getActionId(), mostActiveUsers);
    }
}
