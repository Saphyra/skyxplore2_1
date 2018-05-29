package skyxplore.auth.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import skyxplore.auth.domain.exception.BadCredentialsException;
import skyxplore.auth.domain.exception.BadRequestAuthException;

@ControllerAdvice
@Slf4j
public class AuthExceptionHandlerAdvice {

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity handleBadCredentials(BadCredentialsException ex){
        log.info("Bad credentials: {}", ex.getMessage());
        return new ResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = BadRequestAuthException.class)
    public ResponseEntity handleBadRequest(BadRequestAuthException ex){
        log.info("Bad auth request received: {}", ex.getMessage());
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
