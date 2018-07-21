package skyxplore.exception;

import skyxplore.exception.base.BadRequestException;

public class BadSlotException extends BadRequestException {
    public BadSlotException(String slotId) {
        super("Bad slot id: " + slotId);
    }
}
