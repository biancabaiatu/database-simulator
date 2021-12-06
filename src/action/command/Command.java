package action.command;

import action.Action;
import common.exception.VideoDBsException;
import response.CommandResponse;
import response.Response;
import entertainment.Movie;
import entertainment.Series;
import entertainment.Video;
import entertainment.VideoResource;
import fileio.ActionInputData;
import user.User;
import user.UserResource;
import utils.Utils;

import java.util.Optional;

public final class Command extends Action {

    private final Type type;
    private final User user;

    public Command(final ActionInputData actionInputData) {
        super(actionInputData);
        this.type = Utils.stringToCommandType(actionInputData.getType());
        this.user = UserResource.getInstance()
                .getUserByUsername(getActionInputData().getUsername());
    }

    /**
     * Executes any type of command on the database
     * @return Response to given command type
     */
    public Response execute() {
        return switch (this.type) {
            case FAVOURITE -> addFavourite();
            case RATING -> addRating();
            case VIEW -> viewVideo();
        };
    }

    /**
     * Gives a response when trying to execute the FavoriteCommand
     * @return Response to FavouriteCommand
     */
    private Response addFavourite() {
        Optional<? extends Video> result =
                VideoResource.getInstance()
                        .getVideoByName(getActionInputData().getTitle());

        if (result.isEmpty()) {
            return new Response(
                    getActionInputData().getActionId(),
                    "error -> video not found");
        }

        final Video video = result.get();
        try {
            user.addFavourite(video);
            return new CommandResponse(
                    getActionInputData().getActionId(),
                    video.getTitle() + " was added as favourite",
                    CommandResponse.Type.SUCCESS);
        } catch (VideoDBsException e) {
            return new CommandResponse(
                    getActionInputData().getActionId(),
                    e.getMessage(),
                    CommandResponse.Type.ERROR);
        }

    }

    /**
     * Gives a response when trying to execute the RatingCommand
     * @return Response to RatingCommand
     */
    private Response addRating() {
        Optional<? extends Video> result =
                VideoResource.getInstance()
                        .getVideoByName(getActionInputData().getTitle());

        if (result.isEmpty()) {
            return new Response(
                    getActionInputData().getActionId(),
                    "error -> video not found");
        }

        Video video = result.get();
        double rating = getActionInputData().getGrade();

        if (video instanceof Movie) {
            try {
                ((Movie) video).addRating(user, rating);
                return new CommandResponse(
                        getActionInputData().getActionId(),
                        video.getTitle()
                                + " was rated with "
                                + rating
                                + " by "
                                + user.getUsername(),
                        CommandResponse.Type.SUCCESS);
            } catch (VideoDBsException e) {
                return new CommandResponse(
                        getActionInputData().getActionId(),
                        e.getMessage(),
                        CommandResponse.Type.ERROR);
            }
        } else {
            int seasonNumber = getActionInputData().getSeasonNumber();
            try {
                ((Series) video).addRating(user, rating, seasonNumber);
                return new CommandResponse(
                        getActionInputData().getActionId(),
                        video.getTitle()
                                + " was rated with "
                                + rating + " by "
                                + user.getUsername(),
                        CommandResponse.Type.SUCCESS);
            } catch (VideoDBsException e) {
                return new CommandResponse(
                        getActionInputData().getActionId(),
                        e.getMessage(),
                        CommandResponse.Type.ERROR);
            }
        }
    }

    /**
     * Gives a response when trying to execute the ViewCommand
     * @return Response to ViewCommand
     */
    private Response viewVideo() {
        Optional<? extends Video> result =
                VideoResource.getInstance()
                        .getVideoByName(getActionInputData().getTitle());

        if (result.isEmpty()) {
            return new Response(
                    getActionInputData().getActionId(),
                    "error -> video not found");
        }

        final Video video = result.get();
        user.viewVideo(video);

        return new CommandResponse(
                getActionInputData().getActionId(),
                video.getTitle() + " was viewed with total views of "
                        + user.getHistory().get(video.getTitle()),
                CommandResponse.Type.SUCCESS);

    }

    public enum Type {
        FAVOURITE, VIEW, RATING
    }

}
