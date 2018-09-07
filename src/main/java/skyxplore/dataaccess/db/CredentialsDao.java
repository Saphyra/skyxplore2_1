package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.CredentialsRepository;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.credentials.CredentialsConverter;

@Slf4j
@RequiredArgsConstructor
@Component
public class CredentialsDao {
    private final CredentialsRepository credentialsRepository;
    private final CredentialsConverter credentialsConverter;

    //TODO unit test
    public void delete(String userId) {
        credentialsRepository.deleteById(userId);
    }

    //TODO unit test
    public void save(Credentials credentials) {
        credentialsRepository.save(credentialsConverter.convertDomain(credentials));
    }

    //TODO unit test
    public Credentials getCredentialsByName(String userName) {
        return credentialsConverter.convertEntity(credentialsRepository.getByUserName(userName));
    }

    //TODO unit test
    public Credentials getByUserId(String userId) {
        return credentialsRepository.findById(userId).map(credentialsConverter::convertEntity).orElse(null);
    }
}
