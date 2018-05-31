package skyxplore.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import skyxplore.exception.InvalidAccessException;

@ControllerAdvice
@Slf4j
@Order()
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<String> handleInvalidAccess(InvalidAccessException e){
        log.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException ex){
        log.warn("Internal Server Error: {}. Message: {}", ex.getClass().getName(), ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleAll(Exception ex){
        log.error("Unknown Server Error: {}. Message: {}", ex.getClass().getName(), ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
