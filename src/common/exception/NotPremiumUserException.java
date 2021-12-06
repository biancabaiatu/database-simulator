package common.exception;

import user.User;

public class NotPremiumUserException extends VideoDBsException {

    public NotPremiumUserException(final User user) {
        super(user.getUsername() + "is not a premium user");
    }

}
