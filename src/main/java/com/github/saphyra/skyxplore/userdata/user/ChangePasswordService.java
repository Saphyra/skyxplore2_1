package com.github.saphyra.skyxplore.userdata.user;

import com.github.saphyra.encryption.impl.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import org.springframework.stereotype.Service;
import com.github.saphyra.skyxplore.userdata.user.domain.ChangePasswordRequest;
import com.github.saphyra.skyxplore.common.exception.BadCredentialsException;

@Service
@RequiredArgsConstructor
@Slf4j
class ChangePasswordService {
    private final CredentialsService credentialsService;
    private final PasswordService passwordService;

    void changePassword(ChangePasswordRequest request, String userId) {
        SkyXpCredentials skyXpCredentials = credentialsService.findByUserId(userId);
        validateChangePasswordRequest(request, skyXpCredentials);
        skyXpCredentials.setPassword(passwordService.hashPassword(request.getNewPassword()));
        log.info("Changing password of user " + userId);
        credentialsService.save(skyXpCredentials);
        log.info("Password successfully changed.");
    }

    private void validateChangePasswordRequest(ChangePasswordRequest request, SkyXpCredentials skyXpCredentials) {
        if (!passwordService.authenticate(request.getOldPassword(), skyXpCredentials.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
    }
}
