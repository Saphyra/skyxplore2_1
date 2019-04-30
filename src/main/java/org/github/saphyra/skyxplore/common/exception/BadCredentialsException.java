package org.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;

public class BadCredentialsException extends UnauthorizedException {
    public BadCredentialsException(String message) {
        super(message);
    }
}
