package skyxplore.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.AccountDeleteRequest;
import skyxplore.controller.request.ChangeEmailRequest;
import skyxplore.controller.request.ChangePasswordRequest;
import skyxplore.controller.request.ChangeUserNameRequest;
import skyxplore.controller.request.UserRegistrationRequest;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.service.user.ChangeEmailService;
import skyxplore.service.user.ChangePasswordService;
import skyxplore.service.user.ChangeUserNameService;
import skyxplore.service.user.DeleteAccountService;
import skyxplore.service.user.RegistrationService;
import skyxplore.service.user.UserQueryService;

@SuppressWarnings({"WeakerAccess", "ArraysAsListWithZeroOrOneArgument"})
@Service
@RequiredArgsConstructor
@Slf4j
public class UserFacade {
    private final ChangeEmailService changeEmailService;
    private final ChangePasswordService changePasswordService;
    private final ChangeUserNameService changeUserNameService;
    private final DeleteAccountService deleteAccountService;
    private final RegistrationService registrationService;
    private final UserQueryService userQueryService;

    public void changeEmail(ChangeEmailRequest request, String userId) {
        changeEmailService.changeEmail(request, userId);
    }

    public void changePassword(ChangePasswordRequest request, String userId) {
        changePasswordService.changePassword(request, userId);
    }

    public void changeUserName(ChangeUserNameRequest request, String userId) {
        changeUserNameService.changeUserName(request, userId);
    }

    public void deleteAccount(AccountDeleteRequest request, String userId) {
        deleteAccountService.deleteAccount(request, userId);
    }

    public SkyXpUser getUserById(String userId) {
        return userQueryService.getUserById(userId);
    }

    public SkyXpUser getUserByName(String userName){
        return userQueryService.getUserByName(userName);
    }

    public boolean isEmailExists(String email) {
        return userQueryService.isEmailExists(email);
    }

    public boolean isUserNameExists(String userName) {
        return userQueryService.isUserNameExists(userName);
    }

    public void registrateUser(UserRegistrationRequest request) {
        registrationService.registrateUser(request);
    }
}
