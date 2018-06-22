package skyxplore.exception;

import skyxplore.exception.base.ConflictException;

public class CharacterNameAlreadyExistsException extends ConflictException {
    public CharacterNameAlreadyExistsException(String message){
        super(message);
    }
}
