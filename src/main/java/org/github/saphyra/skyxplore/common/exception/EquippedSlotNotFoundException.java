package org.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class EquippedSlotNotFoundException extends NotFoundException {
    public EquippedSlotNotFoundException(String logMessage) {
        super(logMessage);
    }
}
