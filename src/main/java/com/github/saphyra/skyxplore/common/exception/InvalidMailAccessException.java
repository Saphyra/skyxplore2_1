package com.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;

public class InvalidMailAccessException extends UnauthorizedException {
    public InvalidMailAccessException(String message) {
        super(message);
    }
}
