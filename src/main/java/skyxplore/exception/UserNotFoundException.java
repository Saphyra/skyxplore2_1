package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message){
        super(message);
    }
}
