package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class FriendRequestNotFoundException extends NotFoundException {
    public FriendRequestNotFoundException(String message) {
        super(message);
    }
}
