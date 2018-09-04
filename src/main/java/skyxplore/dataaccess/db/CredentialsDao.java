package skyxplore.dataaccess.db;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.repository.CredentialsRepository;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.credentials.CredentialsConverter;
import skyxplore.domain.credentials.CredentialsEntity;

@Slf4j
@RequiredArgsConstructor
@Component
//TODO unit test
public class CredentialsDao {
    private final CredentialsRepository credentialsRepository;
    private final CredentialsConverter credentialsConverter;

    public void delete(String userId) {
        Optional<CredentialsEntity> optional = credentialsRepository.findById(userId);
        optional.ifPresent(credentialsRepository::delete);

    }

    public void save(Credentials credentials) {
        credentialsRepository.save(credentialsConverter.convertDomain(credentials));
    }

    public Credentials getCredentialsByName(String userName) {
        return credentialsConverter.convertEntity(credentialsRepository.getByUserName(userName));
    }

    public Credentials getByUserId(String userId) {
        Optional<CredentialsEntity> optional = credentialsRepository.findById(userId);
        return optional.map(credentialsConverter::convertEntity).orElse(null);
    }
}
