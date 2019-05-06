package org.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.RestException;
import org.springframework.http.HttpStatus;

public class CharacterBlockedException extends RestException {
    public CharacterBlockedException(String message) {
        super(HttpStatus.LOCKED, message);
    }
}
