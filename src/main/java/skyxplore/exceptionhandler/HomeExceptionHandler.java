package skyxplore.exceptionhandler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.exception.UserNameAlreadyExistsException;

@ControllerAdvice
@Order(0)
public class HomeExceptionHandler {

    @ExceptionHandler(BadlyConfirmedPasswordException.class)
    public ResponseEntity<String> handleBadPassword(){
        return new ResponseEntity<>("Password missmatch.",HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<String> handleUserNameExists(){
        return new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailExists(){
        return new ResponseEntity<>("Email already exists.", HttpStatus.CONFLICT);
    }
}
