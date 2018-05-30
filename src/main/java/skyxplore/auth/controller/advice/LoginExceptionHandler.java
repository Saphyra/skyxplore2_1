package skyxplore.auth.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import skyxplore.auth.domain.exception.BadCredentialsException;

@ControllerAdvice
@Slf4j
@Order(0)
public class LoginExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentials(BadCredentialsException e){
        log.info("Bad credentials: {}", e.getMessage());
        return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
