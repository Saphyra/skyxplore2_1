package org.github.saphyra.skyxplore.auth.repository;

import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class SkyXpAccessTokenConverter extends ConverterBase<AccessTokenEntity, SkyXpAccessToken> {
    private final DateTimeUtil dateTimeUtil;


    @Override
    public SkyXpAccessToken processEntityConversion(AccessTokenEntity entity) {
        if (entity == null) {
            return null;
        }
        return SkyXpAccessToken.builder()
            .accessTokenId(entity.getAccessTokenId())
            .userId(entity.getUserId())
            .lastAccess(dateTimeUtil.convertEntity(entity.getLastAccess()))
            .characterId(entity.getCharacterId())
            .build();
    }

    @Override
    public AccessTokenEntity processDomainConversion(SkyXpAccessToken token) {
        if (token == null) {
            throw new IllegalArgumentException("token must not be null");
        }
        AccessTokenEntity entity = new AccessTokenEntity();
        entity.setAccessTokenId(token.getAccessTokenId());
        entity.setUserId(token.getUserId());
        entity.setLastAccess(dateTimeUtil.convertDomain(token.getLastAccess()));
        entity.setCharacterId(token.getCharacterId());
        return entity;
    }
}
