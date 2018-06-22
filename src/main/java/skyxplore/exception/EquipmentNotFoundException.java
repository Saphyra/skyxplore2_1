package skyxplore.exception;

import skyxplore.exception.base.NotFoundException;

public class EquipmentNotFoundException extends NotFoundException {
    public EquipmentNotFoundException(String message) {
        super(message);
    }
}
