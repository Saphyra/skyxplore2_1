package com.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.ConflictException;

public class UserNameAlreadyExistsException extends ConflictException {
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
