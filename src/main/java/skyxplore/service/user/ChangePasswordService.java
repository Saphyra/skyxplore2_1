package skyxplore.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.user.ChangePasswordRequest;
import skyxplore.domain.credentials.Credentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.service.credentials.CredentialsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangePasswordService {
    private final CredentialsService credentialsService;

    public void changePassword(ChangePasswordRequest request, String userId) {
        Credentials credentials = credentialsService.getByUserId(userId);
        validateChangePasswordRequest(request, credentials);
        credentials.setPassword(request.getNewPassword());
        log.info("Changing password of user " + userId);
        credentialsService.save(credentials);
        log.info("Password successfully changed.");
    }

    private void validateChangePasswordRequest(ChangePasswordRequest request, Credentials user) {
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadlyConfirmedPasswordException("Confirm password does not match.");
        }
    }
}
