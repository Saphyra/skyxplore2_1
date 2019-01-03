package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;

public class CharacterBlockedException extends ForbiddenException {
    public CharacterBlockedException(String message) {
        super(message);
    }
}
