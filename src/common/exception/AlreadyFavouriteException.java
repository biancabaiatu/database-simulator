package common.exception;

public class AlreadyFavouriteException extends VideoDBsException {

    public AlreadyFavouriteException(final String videoTitle) {
        super(videoTitle + " is already in favourite list");
    }

}
