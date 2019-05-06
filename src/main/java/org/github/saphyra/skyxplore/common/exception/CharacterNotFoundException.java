package org.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class CharacterNotFoundException extends NotFoundException {
    public CharacterNotFoundException(String message) {
        super(message);
    }
}
