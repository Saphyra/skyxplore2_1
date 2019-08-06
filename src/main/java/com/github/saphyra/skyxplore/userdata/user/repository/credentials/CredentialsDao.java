package com.github.saphyra.skyxplore.userdata.user.repository.credentials;

import java.util.Optional;

import com.github.saphyra.skyxplore.common.event.AccountDeletedEvent;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;

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

    public Optional<SkyXpCredentials> findByName(String userName) {
        log.debug("Querying Credentials for username {}", userName);
        return converter.convertEntity(repository.findByUserName(userName));
    }
}
