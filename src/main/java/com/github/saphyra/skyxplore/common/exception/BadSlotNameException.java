package com.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;

public class BadSlotNameException extends BadRequestException {
    public BadSlotNameException(String slotId) {
        super("Bad slot id: " + slotId);
    }
}
