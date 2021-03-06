package com.github.saphyra.skyxplore.userdata.user;

import com.github.saphyra.encryption.impl.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.user.cache.EmailCache;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.userdata.user.repository.user.UserDao;
import org.springframework.stereotype.Service;
import com.github.saphyra.skyxplore.userdata.user.domain.ChangeEmailRequest;
import com.github.saphyra.skyxplore.common.exception.BadCredentialsException;
import com.github.saphyra.skyxplore.common.exception.EmailAlreadyExistsException;

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
            throw new EmailAlreadyExistsException(request.getNewEmail() + " email is already exists.");
        }

        SkyXpCredentials skyXpCredentials = credentialsService.findByUserId(userId);
        if (!passwordService.authenticate(request.getPassword(), skyXpCredentials.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        user.setEmail(request.getNewEmail());
        log.info("Changing email of user {}", userId);
        userDao.save(user);
        log.info("Email changed successfully.");

        emailCache.invalidate(request.getNewEmail());
    }
}
