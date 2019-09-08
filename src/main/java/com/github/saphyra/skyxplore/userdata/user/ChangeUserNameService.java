package com.github.saphyra.skyxplore.userdata.user;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.user.cache.UserNameCache;
import com.github.saphyra.skyxplore.userdata.user.domain.ChangeUserNameRequest;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class ChangeUserNameService {
    private final PasswordService passwordService;
    private final CredentialsService credentialsService;
    private final UserNameCache userNameCache;

    void changeUserName(ChangeUserNameRequest request, String userId) {
        SkyXpCredentials skyXpCredentials = credentialsService.findByUserId(userId);
        if (credentialsService.isUserNameExists(request.getNewUserName())) {
            throw ExceptionFactory.userNameAlreadyExists(request.getNewUserName());
        }
        if (!passwordService.authenticate(request.getPassword(), skyXpCredentials.getPassword())) {
            throw ExceptionFactory.wrongPassword();
        }
        skyXpCredentials.setUserName(request.getNewUserName());
        log.info("Changing username of user {}", userId);
        credentialsService.save(skyXpCredentials);
        log.info("Username successfully changed.");

        userNameCache.invalidate(request.getNewUserName());
    }
}
