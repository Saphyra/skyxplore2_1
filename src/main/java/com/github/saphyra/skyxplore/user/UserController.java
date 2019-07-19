package com.github.saphyra.skyxplore.user;

import com.github.saphyra.skyxplore.user.cache.UserNameCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.saphyra.skyxplore.user.cache.EmailCache;
import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.user.domain.AccountDeleteRequest;
import com.github.saphyra.skyxplore.user.domain.ChangeEmailRequest;
import com.github.saphyra.skyxplore.user.domain.ChangePasswordRequest;
import com.github.saphyra.skyxplore.user.domain.ChangeUserNameRequest;
import com.github.saphyra.skyxplore.user.domain.UserRegistrationRequest;

import javax.validation.Valid;

import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_USER_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
class UserController {
    private static final String CHANGE_EMAIL_MAPPING = "user/email";
    private static final String CHANGE_PASSWORD_MAPPING = "user/password";
    private static final String CHANGE_USERNAME_MAPPING = "user/name";
    private static final String DELETE_ACCOUNT_MAPPING = "user";
    private static final String EMAIL_EXISTS_MAPPING = "user/email";
    private static final String REGISTRATION_MAPPING = "user";
    private static final String USERNAME_EXISTS_MAPPING = "user/name";

    private final UserNameCache userNameCache;
    private final EmailCache emailCache;
    private final ChangeEmailService changeEmailService;
    private final ChangePasswordService changePasswordService;
    private final ChangeUserNameService changeUserNameService;
    private final DeleteAccountService deleteAccountService;
    private final RegistrationService registrationService;

    @PutMapping(CHANGE_EMAIL_MAPPING)
    void changeEmail(
        @RequestBody @Valid ChangeEmailRequest request,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to change his email address.", userId);
        changeEmailService.changeEmail(request, userId);
    }

    @PutMapping(CHANGE_PASSWORD_MAPPING)
    void changePassword(
        @RequestBody @Valid ChangePasswordRequest request,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to change his password.", userId);
        changePasswordService.changePassword(request, userId);
    }

    @PutMapping(CHANGE_USERNAME_MAPPING)
    void changeUserName(
        @RequestBody @Valid ChangeUserNameRequest request,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to change his username.", userId);
        changeUserNameService.changeUserName(request, userId);
        userNameCache.invalidate(request.getNewUserName());
    }

    @DeleteMapping(DELETE_ACCOUNT_MAPPING)
    void deleteAccount(
        @RequestBody @Valid AccountDeleteRequest request,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to deleteById his account");
        deleteAccountService.deleteAccount(request, userId);
    }

    @PostMapping(EMAIL_EXISTS_MAPPING)
    boolean isEmailExists(@RequestBody @Valid OneStringParamRequest email) {
        log.info("Request arrived to {}, request parameter: {}", EMAIL_EXISTS_MAPPING, email.getValue());
        return emailCache.get(email.getValue()).orElse(true);
    }

    @PostMapping(REGISTRATION_MAPPING)
    void registration(@RequestBody @Valid UserRegistrationRequest request) {
        log.info("{} wants to register!", request.getUsername());
        registrationService.registerUser(request);
        userNameCache.invalidate(request.getUsername());
        emailCache.invalidate(request.getEmail());
    }

    @PostMapping(USERNAME_EXISTS_MAPPING)
    boolean isUsernameExists(@RequestBody @Valid OneStringParamRequest userName) {
        log.info("Request arrived to {}, request parameter: {}", USERNAME_EXISTS_MAPPING, userName.getValue());
        return userNameCache.get(userName.getValue()).orElse(true);
    }
}
