package skyxplore.exception;

import skyxplore.exception.base.ConflictException;

public class FriendshipAlreadyExistsException extends ConflictException {
    public FriendshipAlreadyExistsException(String message) {
        super(message);
    }

    public FriendshipAlreadyExistsException(String friendId, String characterId) {
        super("Friendship already exists (or pending) between " + characterId + " and " + friendId);
    }
}
