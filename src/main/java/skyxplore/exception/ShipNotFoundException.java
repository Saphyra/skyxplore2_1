package skyxplore.exception;

public class ShipNotFoundException extends RuntimeException {
    public ShipNotFoundException(String message){
        super(message);
    }
}
