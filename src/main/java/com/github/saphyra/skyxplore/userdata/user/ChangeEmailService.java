package com.github.saphyra.skyxplore.userdata.user;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.user.cache.EmailCache;
import com.github.saphyra.skyxplore.userdata.user.domain.ChangeEmailRequest;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.userdata.user.repository.user.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class ChangeEmailService {
    private final PasswordService passwordService;
    private final CredentialsService credentialsService;
    private final UserQueryService userQueryService;
    private final UserDao userDao;
    private final EmailCache emailCache;

    void changeEmail(ChangeEmailRequest request, String userId) {
        SkyXpUser user = userQueryService.getUserById(userId);
        if (userQueryService.isEmailExists(request.getNewEmail())) {
            throw ExceptionFactory.emailAlreadyExists(request.getNewEmail());
        }

        SkyXpCredentials skyXpCredentials = credentialsService.findByUserId(userId);
        if (!passwordService.authenticate(request.getPassword(), skyXpCredentials.getPassword())) {
            throw ExceptionFactory.wrongPassword();
        }
        user.setEmail(request.getNewEmail());
        log.info("Changing email of user {}", userId);
        userDao.save(user);
        log.info("Email changed successfully.");

        emailCache.invalidate(request.getNewEmail());
    }
}
