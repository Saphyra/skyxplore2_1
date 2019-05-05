package org.github.saphyra.skyxplore.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.common.exception.BadCredentialsException;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CredentialsService {
    private final CredentialsDao credentialsDao;

    public SkyXpCredentials findByUserId(String userId) {
        return credentialsDao.findById(userId)
            .orElseThrow(() -> new BadCredentialsException("SkyXpCredentials cannot be found. UserId: " + userId));
    }

    public boolean isUserNameExists(String userName) {
        return credentialsDao.findByName(userName).isPresent();
    }

    public void save(SkyXpCredentials skyXpCredentials) {
        credentialsDao.save(skyXpCredentials);
    }
}
