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
    private final CredentialsConverter credentialsConverter;
    private final CredentialsRepository credentialsRepository;

    public void delete(String userId) {
        credentialsRepository.deleteById(userId);
    }

    public Credentials getByUserId(String userId) {
        return credentialsRepository.findById(userId).map(credentialsConverter::convertEntity).orElse(null);
    }

    public Credentials getCredentialsByName(String userName) {
        return credentialsConverter.convertEntity(credentialsRepository.getByUserName(userName));
    }

    public void save(Credentials credentials) {
        credentialsRepository.save(credentialsConverter.convertDomain(credentials));
    }
}
