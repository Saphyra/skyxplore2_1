package skyxplore.dataaccess.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.CredentialsRepository;
import com.github.saphyra.converter.Converter;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.credentials.CredentialsEntity;

@Slf4j
@Component
public class CredentialsDao extends AbstractDao<CredentialsEntity, Credentials, String, CredentialsRepository>{

    public CredentialsDao(Converter<CredentialsEntity, Credentials> converter, CredentialsRepository repository) {
        super(converter, repository);
    }

    public Credentials getByUserId(String userId) {
        return repository.findById(userId).map(converter::convertEntity).orElse(null);
    }

    public Credentials getCredentialsByName(String userName) {
        return converter.convertEntity(repository.getByUserName(userName));
    }
}
