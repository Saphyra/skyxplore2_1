package com.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;

public class NotEnoughMaterialsException extends ForbiddenException {
    public NotEnoughMaterialsException(String message) {
        super(message);
    }
}
