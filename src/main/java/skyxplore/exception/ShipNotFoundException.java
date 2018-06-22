package skyxplore.exception;

import skyxplore.exception.base.NotFoundException;

public class ShipNotFoundException extends NotFoundException {
    public ShipNotFoundException(String message){
        super(message);
    }
}
