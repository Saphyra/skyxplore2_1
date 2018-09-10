package skyxplore.controller;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.request.user.*;
import skyxplore.filter.AuthFilter;
import skyxplore.service.UserFacade;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("ALL")
@RestController
@RequiredArgsConstructor
@Slf4j
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
    public void changeEmail(
        @RequestBody @Valid ChangeEmailRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to change his email address.", userId);
        userFacade.changeEmail(request, userId);
    }

    @PostMapping(CHANGE_PASSWORD_MAPPING)
    public void changePassword(
        @RequestBody @Valid ChangePasswordRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to change his password.", userId);
        userFacade.changePassword(request, userId);
    }

    @PostMapping(CHANGE_USERNAME_MAPPING)
    public void changeUserName(
        @RequestBody @Valid ChangeUserNameRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to change his username.", userId);
        userFacade.changeUserName(request, userId);
        userNameCache.invalidate(request.getNewUserName());
    }

    @PostMapping(DELETE_ACCOUNT_MAPPING)
    public void deleteAccount(
        @RequestBody @Valid AccountDeleteRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to deleteById his account");
        userFacade.deleteAccount(request, userId);
    }

    @PostMapping(EMAIL_EXISTS_MAPPING)
    public boolean isEmailExists(@RequestBody @Valid OneStringParamRequest email) throws ExecutionException {
        log.info("Request arrived to {}, request parameter: {}", EMAIL_EXISTS_MAPPING, email.getValue());
        return emailCache.get(email.getValue());
    }

    @PostMapping(REGISTRATION_MAPPING)
    public void registration(@RequestBody @Valid UserRegistrationRequest request) {
        log.info("{} wants to register!", request.getUsername());
        userFacade.registrateUser(request);
        userNameCache.invalidate(request.getUsername());
        emailCache.invalidate(request.getEmail());
    }

    @PostMapping(USERNAME_EXISTS_MAPPING)
    public boolean isUsernameExists(@RequestBody @Valid OneStringParamRequest userName) throws ExecutionException {
        log.info("Request arrived to {}, request parameter: {}", USERNAME_EXISTS_MAPPING, userName.getValue());
        return userNameCache.get(userName.getValue());
    }
}
