package skyxplore.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import skyxplore.exception.ShipNotFoundException;

@ControllerAdvice
@Slf4j
public class ShipExceptionHandler {

    @ExceptionHandler(ShipNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ShipNotFoundException e){
        log.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
