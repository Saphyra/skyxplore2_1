package com.github.saphyra.skyxplore.user;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.user.cache.UserNameCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.springframework.stereotype.Service;
import com.github.saphyra.skyxplore.user.domain.ChangeUserNameRequest;
import com.github.saphyra.skyxplore.common.exception.BadCredentialsException;
import com.github.saphyra.skyxplore.common.exception.UserNameAlreadyExistsException;

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
            throw new UserNameAlreadyExistsException(request.getNewUserName() + " username is already exists.");
        }
        if (!passwordService.authenticate(request.getPassword(), skyXpCredentials.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        skyXpCredentials.setUserName(request.getNewUserName());
        log.info("Changing username of user {}", userId);
        credentialsService.save(skyXpCredentials);
        log.info("Username successfully changed.");

        userNameCache.invalidate(request.getNewUserName());
    }
}
