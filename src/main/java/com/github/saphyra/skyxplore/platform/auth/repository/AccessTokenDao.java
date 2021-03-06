package com.github.saphyra.skyxplore.platform.auth.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.common.event.AccountDeletedEvent;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class AccessTokenDao extends AbstractDao<AccessTokenEntity, SkyXpAccessToken, String, AccessTokenRepository> {
    private final DateTimeUtil dateTimeUtil;

    public AccessTokenDao(SkyXpAccessTokenConverter converter, AccessTokenRepository repository, DateTimeUtil dateTimeUtil) {
        super(converter, repository);
        this.dateTimeUtil = dateTimeUtil;
    }

    @EventListener
    void accountDeletedEventListener(AccountDeletedEvent event){
        deleteByUserId(event.getUserId());
    }

    public void cleanupCharacterId(String characterId) {
        log.info("Cleaning up characterId {}", characterId);
        repository.cleanUpCharacterId(characterId);
    }

    public void deleteByUserId(String userId) {
        log.info("Deleting accessToken of user {}", userId);
        repository.deleteByUserId(userId);
    }

    public void deleteExpired(OffsetDateTime expiration) {
        repository.deleteExpired(dateTimeUtil.convertDomain(expiration));
    }

    public Optional<SkyXpAccessToken> findByCharacterId(String characterId) {
        return converter.convertEntity(repository.findByCharacterId(characterId));
    }

    public Optional<SkyXpAccessToken> findByUserId(String userId) {
        return converter.convertEntity(repository.findByUserId(userId));
    }
}
