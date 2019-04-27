package org.github.saphyra.skyxplore.auth.repository;

import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

@Component
@RequiredArgsConstructor
class SkyXpAccessTokenConverter extends ConverterBase<AccessTokenEntity, SkyXpAccessToken> {
    private final DateTimeUtil dateTimeUtil;


    @Override
    public SkyXpAccessToken processEntityConversion(AccessTokenEntity entity) {
        if (entity == null) {
            return null;
        }
        SkyXpAccessToken token = new SkyXpAccessToken();
        token.setAccessTokenId(entity.getAccessTokenId());
        token.setUserId(entity.getUserId());
        token.setLastAccess(dateTimeUtil.convertEntity(entity.getLastAccess()));
        token.setCharacterId(entity.getCharacterId());
        return token;
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
