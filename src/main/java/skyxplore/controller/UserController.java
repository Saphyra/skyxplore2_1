package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.cache.EmailCache;
import skyxplore.cache.UserNameCache;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.request.user.*;
import skyxplore.service.UserFacade;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

import static skyxplore.filter.CustomFilterHelper.COOKIE_USER_ID;

@SuppressWarnings("ALL")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private static final String CHANGE_EMAIL_MAPPING = "user/email/change";
    private static final String CHANGE_PASSWORD_MAPPING = "user/password/change";
    private static final String CHANGE_USERNAME_MAPPING = "user/name/change";
    private static final String DELETE_ACCOUNT_MAPPING = "user";
    private static final String EMAIL_EXISTS_MAPPING = "user/email/exist";
    private static final String REGISTRATION_MAPPING = "user/register";
    private static final String USERNAME_EXISTS_MAPPING = "user/name/exist";

    private final UserNameCache userNameCache;
    private final EmailCache emailCache;
    private final UserFacade userFacade;

    @PostMapping(CHANGE_EMAIL_MAPPING)
    public void changeEmail(
        @RequestBody @Valid ChangeEmailRequest request,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to change his email address.", userId);
        userFacade.changeEmail(request, userId);
    }

    @PostMapping(CHANGE_PASSWORD_MAPPING)
    public void changePassword(
        @RequestBody @Valid ChangePasswordRequest request,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to change his password.", userId);
        userFacade.changePassword(request, userId);
    }

    @PostMapping(CHANGE_USERNAME_MAPPING)
    public void changeUserName(
        @RequestBody @Valid ChangeUserNameRequest request,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to change his username.", userId);
        userFacade.changeUserName(request, userId);
        userNameCache.invalidate(request.getNewUserName());
    }

    @DeleteMapping(DELETE_ACCOUNT_MAPPING)
    public void deleteAccount(
        @RequestBody @Valid AccountDeleteRequest request,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to deleteById his account");
        userFacade.deleteAccount(request, userId);
    }

    @PostMapping(EMAIL_EXISTS_MAPPING)
    public boolean isEmailExists(@RequestBody @Valid OneStringParamRequest email) throws ExecutionException {
        log.info("Request arrived to {}, request parameter: {}", EMAIL_EXISTS_MAPPING, email.getValue());
        return emailCache.get(email.getValue()).orElse(true);
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
        return userNameCache.get(userName.getValue()).orElse(true);
    }
}
