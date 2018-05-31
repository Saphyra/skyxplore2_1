package skyxplore.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import skyxplore.exception.CharacterNameAlreadyExistsException;
import skyxplore.exception.CharacterNotFoundException;

@ControllerAdvice
@Slf4j
public class CharacterExceptionHandler {
    @ExceptionHandler(CharacterNameAlreadyExistsException.class)
    public ResponseEntity<String> handleNameExists(CharacterNameAlreadyExistsException e){
        log.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<String> handleNotFound(CharacterNotFoundException e){
        log.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
