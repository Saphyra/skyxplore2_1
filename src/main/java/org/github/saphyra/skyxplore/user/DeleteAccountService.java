package org.github.saphyra.skyxplore.user;

import com.github.saphyra.encryption.impl.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.event.AccountDeletedEvent;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.user.domain.AccountDeleteRequest;
import org.github.saphyra.skyxplore.common.exception.BadCredentialsException;

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
        SkyXpCredentials skyXpCredentials = credentialsService.getByUserId(userId);
        if (!passwordService.authenticate(request.getPassword(), skyXpCredentials.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        applicationEventPublisher.publishEvent(new AccountDeletedEvent(userId));
    }
}
