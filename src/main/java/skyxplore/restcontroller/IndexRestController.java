package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.restcontroller.request.UserRegistrationRequest;
import skyxplore.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IndexRestController {
    private static final String USERNAME_EXISTS_MAPPING = "/isusernameexists";
    private static final String EMAIL_EXISTS_MAPPING = "/isemailexists";
    private static final String REGISTRATION_MAPPING = "/registration";

    private final UserService userService;

    @GetMapping(USERNAME_EXISTS_MAPPING)
    public boolean isUsernameExists(@RequestParam(value = "username", required = true) String userName){
        log.info("Request arrived to {}, request parameter: {}", USERNAME_EXISTS_MAPPING, userName);
        return userService.isUserNameExists(userName);
    }

    @GetMapping(EMAIL_EXISTS_MAPPING)
    public  boolean isEmailExists(@RequestParam(value="email", required = true) String email){
        log.info("Request arrived to {}, request parameter: {}", EMAIL_EXISTS_MAPPING, email);
        return userService.isEmailExists(email);
    }

    @PostMapping(REGISTRATION_MAPPING)
    public Long registration(@RequestBody @Valid UserRegistrationRequest request){
        log.info("{} wants to registrate!", request.getUsername());
        return userService.registrateUser(request);
    }


}
