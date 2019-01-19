package skyxplore.service.user;

import com.github.saphyra.encryption.impl.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.user.ChangePasswordRequest;
import skyxplore.domain.credentials.SkyXpCredentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.service.credentials.CredentialsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangePasswordService {
    private final CredentialsService credentialsService;
    private final PasswordService passwordService;

    public void changePassword(ChangePasswordRequest request, String userId) {
        SkyXpCredentials skyXpCredentials = credentialsService.getByUserId(userId);
        validateChangePasswordRequest(request, skyXpCredentials);
        skyXpCredentials.setPassword(passwordService.hashPassword(request.getNewPassword()));
        log.info("Changing password of user " + userId);
        credentialsService.save(skyXpCredentials);
        log.info("Password successfully changed.");
    }

    private void validateChangePasswordRequest(ChangePasswordRequest request, SkyXpCredentials skyXpCredentials) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadlyConfirmedPasswordException("Confirm password does not match.");
        }
        if (!passwordService.authenticate(request.getOldPassword(), skyXpCredentials.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
    }
}
