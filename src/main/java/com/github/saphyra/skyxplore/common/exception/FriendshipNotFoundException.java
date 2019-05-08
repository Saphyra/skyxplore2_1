package com.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class FriendshipNotFoundException extends NotFoundException {
    public FriendshipNotFoundException(String message) {
        super(message);
    }
}
