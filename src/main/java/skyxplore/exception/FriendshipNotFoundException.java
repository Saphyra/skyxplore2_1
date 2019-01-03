package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class FriendshipNotFoundException extends NotFoundException {
    public FriendshipNotFoundException(String message) {
        super(message);
    }
}
