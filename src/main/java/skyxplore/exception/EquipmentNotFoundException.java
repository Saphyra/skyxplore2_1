package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class EquipmentNotFoundException extends NotFoundException {
    public EquipmentNotFoundException(String message) {
        super(message);
    }
}
