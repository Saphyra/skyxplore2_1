package skyxplore.exception;

import skyxplore.exception.base.BadRequestException;

public class BadSlotNameException extends BadRequestException {
    public BadSlotNameException(String slotId) {
        super("Bad slot id: " + slotId);
    }
}
