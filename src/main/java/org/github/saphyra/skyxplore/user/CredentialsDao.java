package org.github.saphyra.skyxplore.user;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.user.domain.credentials.CredentialsEntity;
import org.github.saphyra.skyxplore.user.domain.credentials.SkyXpCredentials;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CredentialsDao extends AbstractDao<CredentialsEntity, SkyXpCredentials, String, CredentialsRepository> {

    public CredentialsDao(Converter<CredentialsEntity, SkyXpCredentials> converter, CredentialsRepository repository) {
        super(converter, repository);
    }

    public SkyXpCredentials getByUserId(String userId) {
        return repository.findById(userId).map(converter::convertEntity).orElse(null);
    }

    public Optional<SkyXpCredentials> getCredentialsByName(String userName) {
        return converter.convertEntityToOptional(repository.getByUserName(userName));
    }
}