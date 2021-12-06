package action;

import action.command.Command;
import action.query.Query;
import action.recommendation.Recommendation;
import response.Response;
import fileio.ActionInputData;
import utils.Utils;

public abstract class Action {

    private final ActionInputData actionInputData;

    public Action(final ActionInputData actionInputData) {
        this.actionInputData = actionInputData;
    }

    public final ActionInputData getActionInputData() {
        return actionInputData;
    }

    /**
     * Abstract function that gives an answer
     * to any kind of action received during the testing
     * @return Response to the action given
     */
    public abstract Response execute();

    public static class Factory {
        /**
         * Determines and executes any type of action received
         * @param actionInputData input to be tested
         * @return Command, Query, Recommendation depending on the type of action
         */
        public static Action getAction(final ActionInputData actionInputData) {
            Type type = Utils.stringToActionType(actionInputData.getActionType());
            return switch (type) {
                case COMMAND -> new Command(actionInputData);
                case QUERY -> new Query.Builder(actionInputData).build();
                case RECOMMENDATION -> new Recommendation(actionInputData);
            };
        }

    }

    public enum Type {
        COMMAND, QUERY, RECOMMENDATION
    }
}
