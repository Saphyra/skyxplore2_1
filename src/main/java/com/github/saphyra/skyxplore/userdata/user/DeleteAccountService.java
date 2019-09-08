package com.github.saphyra.skyxplore.userdata.user;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.event.AccountDeletedEvent;
import com.github.saphyra.skyxplore.userdata.user.domain.AccountDeleteRequest;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class DeleteAccountService {
    private final PasswordService passwordService;
    private final CredentialsService credentialsService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    void deleteAccount(AccountDeleteRequest request, String userId) {
        SkyXpCredentials skyXpCredentials = credentialsService.findByUserId(userId);
        if (!passwordService.authenticate(request.getPassword(), skyXpCredentials.getPassword())) {
            throw ExceptionFactory.wrongPassword();
        }

        applicationEventPublisher.publishEvent(new AccountDeletedEvent(userId));
    }
}
