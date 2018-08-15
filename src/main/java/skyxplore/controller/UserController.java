package skyxplore.controller;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.filter.AuthFilter;
import skyxplore.controller.request.*;
import skyxplore.service.UserFacade;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("unused")
@RestController
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class UserController {
    private static final String CHANGE_EMAIL_MAPPING = "user/changeemail";
    private static final String CHANGE_PASSWORD_MAPPING = "user/changepassword";
    private static final String CHANGE_USERNAME_MAPPING = "user/changeusername";
    private static final String DELETE_ACCOUNT_MAPPING = "user/deleteaccount";
    private static final String EMAIL_EXISTS_MAPPING = "/isemailexists";
    private static final String REGISTRATION_MAPPING = "/registration";
    private static final String USERNAME_EXISTS_MAPPING = "/isusernameexists";

    private final Cache<String, Boolean> userNameCache;
    private final Cache<String, Boolean> emailCache;
    private final UserFacade userFacade;

    @PostMapping(CHANGE_EMAIL_MAPPING)
    public void changeEmail(@RequestBody @Valid ChangeEmailRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to change his email address.", userId);
        userFacade.changeEmail(request, userId);
    }

    @PostMapping(CHANGE_PASSWORD_MAPPING)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to change his password.", userId);
        userFacade.changePassword(request, userId);
    }

    @PostMapping(CHANGE_USERNAME_MAPPING)
    public void changeUserName(@RequestBody @Valid ChangeUserNameRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to change his username.", userId);
        userFacade.changeUserName(request, userId);
        userNameCache.invalidate(request.getNewUserName());
    }

    @PostMapping(DELETE_ACCOUNT_MAPPING)
    public void deleteAccount(@RequestBody @Valid AccountDeleteRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to delete his account");
        userFacade.deleteAccount(request, userId);
    }

    @GetMapping(EMAIL_EXISTS_MAPPING)
    public  boolean isEmailExists(@RequestParam(value="email") String email) throws ExecutionException {
        log.info("Request arrived to {}, request parameter: {}", EMAIL_EXISTS_MAPPING, email);
        return emailCache.get(email);
    }

    @PostMapping(REGISTRATION_MAPPING)
    public void registration(@RequestBody @Valid UserRegistrationRequest request){
        log.info("{} wants to registrate!", request.getUsername());
        userFacade.registrateUser(request);
        userNameCache.invalidate(request.getUsername());
        emailCache.invalidate(request.getEmail());
    }

    @GetMapping(USERNAME_EXISTS_MAPPING)
    public boolean isUsernameExists(@RequestParam(value = "username") String userName) throws ExecutionException {
        log.info("Request arrived to {}, request parameter: {}", USERNAME_EXISTS_MAPPING, userName);
        return userNameCache.get(userName);
    }
}
