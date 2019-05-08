package com.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.ConflictException;

public class FriendshipAlreadyExistsException extends ConflictException {
    public FriendshipAlreadyExistsException(String friendId, String characterId) {
        super("Friendship already exists (or pending) between " + characterId + " and " + friendId);
    }
}
