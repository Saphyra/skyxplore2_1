package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.ConflictException;

public class CharacterNameAlreadyExistsException extends ConflictException {
    public CharacterNameAlreadyExistsException(String message) {
        super(message);
    }
}
