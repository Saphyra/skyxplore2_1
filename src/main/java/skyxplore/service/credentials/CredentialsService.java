package skyxplore.service.credentials;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.CredentialsDao;
import skyxplore.domain.credentials.SkyXpCredentials;
import skyxplore.exception.BadCredentialsException;

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

    public SkyXpCredentials getCredentialsByName(String userName) {
        return credentialsDao.getCredentialsByName(userName)
            .orElseThrow(() -> new BadCredentialsException("SkyXpCredentials cannot be found. Username: " + userName));
    }

    public boolean isUserNameExists(String userName) {
        return credentialsDao.getCredentialsByName(userName).isPresent();
    }

    public void save(SkyXpCredentials skyXpCredentials) {
        credentialsDao.save(skyXpCredentials);
    }
}
