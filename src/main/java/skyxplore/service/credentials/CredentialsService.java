package skyxplore.service.credentials;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.CredentialsDao;
import skyxplore.domain.credentials.Credentials;
import skyxplore.exception.BadCredentialsException;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class CredentialsService {
    private final CredentialsDao credentialsDao;

    public Credentials getByUserId(String userId) {
        Credentials credentials = credentialsDao.getByUserId(userId);
        if (credentials == null) {
            throw new BadCredentialsException("Credentials cannot be found. UserId: " + userId);
        }
        return credentials;
    }

    public Credentials getCredentialsByName(String userName) {
        Credentials credentials = credentialsDao.getCredentialsByName(userName);
        if (credentials == null) {
            throw new BadCredentialsException("Credentials cannot be found. Username: " + userName);
        }
        return credentials;
    }

    public boolean isUserNameExists(String userName) {
        return credentialsDao.getCredentialsByName(userName) != null;
    }

    public void save(Credentials credentials) {
        credentialsDao.save(credentials);
    }


}
