package org.github.saphyra.skyxplore.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.common.exception.BadCredentialsException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CredentialsService {
    private final CredentialsDao credentialsDao;

    public SkyXpCredentials getByUserId(String userId) {
        SkyXpCredentials skyXpCredentials = credentialsDao.getByUserId(userId);
        if (skyXpCredentials == null) {
            throw new BadCredentialsException("SkyXpCredentials cannot be found. UserId: " + userId);
        }
        return skyXpCredentials;
    }

    public boolean isUserNameExists(String userName) {
        return credentialsDao.getCredentialsByName(userName).isPresent();
    }

    public void save(SkyXpCredentials skyXpCredentials) {
        credentialsDao.save(skyXpCredentials);
    }
}
