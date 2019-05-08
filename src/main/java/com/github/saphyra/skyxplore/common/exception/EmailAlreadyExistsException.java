package com.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.ConflictException;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
