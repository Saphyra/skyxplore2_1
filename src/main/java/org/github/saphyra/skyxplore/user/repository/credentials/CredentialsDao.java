package org.github.saphyra.skyxplore.user.repository.credentials;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.event.AccountDeletedEvent;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CredentialsDao extends AbstractDao<CredentialsEntity, SkyXpCredentials, String, CredentialsRepository> {

    public CredentialsDao(Converter<CredentialsEntity, SkyXpCredentials> converter, CredentialsRepository repository) {
        super(converter, repository);
    }

    @EventListener
    void accountDeletedEventListener(AccountDeletedEvent accountDeletedEvent) {
        log.info("Deleting credentials for user {}", accountDeletedEvent.getUserId());
        repository.deleteById(accountDeletedEvent.getUserId());
    }

    public SkyXpCredentials getByUserId(String userId) {
        return repository.findById(userId).map(converter::convertEntity).orElse(null);
    }

    public Optional<SkyXpCredentials> getCredentialsByName(String userName) {
        return converter.convertEntityToOptional(repository.getByUserName(userName));
    }
}
