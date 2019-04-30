package org.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class ShipNotFoundException extends NotFoundException {
    public ShipNotFoundException(String message) {
        super(message);
    }
}
