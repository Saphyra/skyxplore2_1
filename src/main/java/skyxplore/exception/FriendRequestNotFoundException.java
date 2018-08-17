package skyxplore.exception;

import skyxplore.exception.base.NotFoundException;

public class FriendRequestNotFoundException extends NotFoundException {
    public FriendRequestNotFoundException(String message) {
        super(message);
    }
}
