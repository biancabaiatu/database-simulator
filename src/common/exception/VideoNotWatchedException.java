package common.exception;

public class VideoNotWatchedException extends VideoDBsException {

    public VideoNotWatchedException(final String videoTitle) {
        super(videoTitle + " is not seen");
    }

}
