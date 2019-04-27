package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class CharacterNotFoundException extends NotFoundException {
    public CharacterNotFoundException(String message) {
        super(message);
    }
}
