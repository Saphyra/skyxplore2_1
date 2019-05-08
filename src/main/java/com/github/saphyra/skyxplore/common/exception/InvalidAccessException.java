package com.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;

public class InvalidAccessException extends UnauthorizedException {
    public InvalidAccessException(String message) {
        super(message);
    }
}
