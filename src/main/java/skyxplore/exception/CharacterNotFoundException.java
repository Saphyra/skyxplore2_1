package skyxplore.exception;

import skyxplore.exception.base.NotFoundException;

public class CharacterNotFoundException extends NotFoundException {
    public CharacterNotFoundException(String message){
        super(message);
    }
}
