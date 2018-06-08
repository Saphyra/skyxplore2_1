package skyxplore.restcontroller;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.filter.AuthFilter;
import skyxplore.restcontroller.request.ChangePasswordRequest;
import skyxplore.restcontroller.request.ChangeUserNameRequest;
import skyxplore.restcontroller.request.UserRegistrationRequest;
import skyxplore.service.UserService;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private static final String CHANGE_PASSWORD_MAPPING = "user/changepassword";
    private static final String CHANGE_USERNAME_MAPPING = "user/changeusername";
    private static final String EMAIL_EXISTS_MAPPING = "/isemailexists";
    private static final String REGISTRATION_MAPPING = "/registration";
    private static final String USERNAME_EXISTS_MAPPING = "/isusernameexists";


    private final Cache<String, Boolean> userNameCache;
    private final Cache<String, Boolean> emailCache;
    private final UserService userService;

    @PostMapping(CHANGE_PASSWORD_MAPPING)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) Long userId){
        log.info("{} wants to change his password.", userId);
        userService.changePassword(request, userId);
    }

    @PostMapping(CHANGE_USERNAME_MAPPING)
    public void changeUserName(@RequestBody @Valid ChangeUserNameRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) Long userId){
        log.info("{} wants to change his username.", userId);
        userService.changeUserName(request, userId);
        userNameCache.invalidate(request.getNewUserName());
    }

    @GetMapping(EMAIL_EXISTS_MAPPING)
    public  boolean isEmailExists(@RequestParam(value="email", required = true) String email) throws ExecutionException {
        log.info("Request arrived to {}, request parameter: {}", EMAIL_EXISTS_MAPPING, email);
        return emailCache.get(email);
    }

    @PostMapping(REGISTRATION_MAPPING)
    public void registration(@RequestBody @Valid UserRegistrationRequest request){
        log.info("{} wants to registrate!", request.getUsername());
        userService.registrateUser(request);
        userNameCache.invalidate(request.getUsername());
        emailCache.invalidate(request.getEmail());
    }

    @GetMapping(USERNAME_EXISTS_MAPPING)
    public boolean isUsernameExists(@RequestParam(value = "username", required = true) String userName) throws ExecutionException {
        log.info("Request arrived to {}, request parameter: {}", USERNAME_EXISTS_MAPPING, userName);
        return userNameCache.get(userName);
    }
}
