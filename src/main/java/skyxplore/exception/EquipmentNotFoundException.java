package skyxplore.exception;

public class EquipmentNotFoundException extends RuntimeException {
    public EquipmentNotFoundException(String message) {
        super(message);
    }
}