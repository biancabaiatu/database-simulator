package common.exception;

public class AlreadyRatedByUserException extends VideoDBsException {

    public AlreadyRatedByUserException(final String videoTitle) {
        super(videoTitle + " has been already rated");
    }

}
