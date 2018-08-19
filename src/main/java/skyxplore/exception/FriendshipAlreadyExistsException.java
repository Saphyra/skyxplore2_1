package skyxplore.exception;

import skyxplore.controller.request.community.AddFriendRequest;
import skyxplore.exception.base.ConflictException;

public class FriendshipAlreadyExistsException extends ConflictException {
    public FriendshipAlreadyExistsException(String message) {
        super(message);
    }

    public FriendshipAlreadyExistsException(AddFriendRequest request) {
        super("Friendship already exists (or pending) between " + request.getCharacterId() + " and " + request.getFriendId());
    }
}
