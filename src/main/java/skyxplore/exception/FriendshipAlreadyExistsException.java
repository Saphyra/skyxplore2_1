package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.ConflictException;

public class FriendshipAlreadyExistsException extends ConflictException {
    public FriendshipAlreadyExistsException(String message) {
        super(message);
    }

    public FriendshipAlreadyExistsException(String friendId, String characterId) {
        super("Friendship already exists (or pending) between " + characterId + " and " + friendId);
    }
}
