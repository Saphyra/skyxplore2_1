package com.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.ConflictException;

public class CharacterNameAlreadyExistsException extends ConflictException {
    public CharacterNameAlreadyExistsException(String message) {
        super(message);
    }
}
