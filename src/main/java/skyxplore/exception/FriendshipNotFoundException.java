package skyxplore.exception;

import skyxplore.exception.base.NotFoundException;

public class FriendshipNotFoundException extends NotFoundException {
    public FriendshipNotFoundException(String message) {
        super(message);
    }
}
