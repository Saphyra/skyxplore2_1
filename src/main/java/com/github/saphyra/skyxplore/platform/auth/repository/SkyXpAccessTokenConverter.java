package com.github.saphyra.skyxplore.platform.auth.repository;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SkyXpAccessTokenConverter extends ConverterBase<AccessTokenEntity, SkyXpAccessToken> {
    private final DateTimeUtil dateTimeUtil;


    @Override
    public SkyXpAccessToken processEntityConversion(AccessTokenEntity entity) {
        return SkyXpAccessToken.builder()
            .accessTokenId(entity.getAccessTokenId())
            .userId(entity.getUserId())
            .lastAccess(dateTimeUtil.convertEntity(entity.getLastAccess()))
            .characterId(entity.getCharacterId())
            .build();
    }

    @Override
    public AccessTokenEntity processDomainConversion(SkyXpAccessToken token) {
        return AccessTokenEntity.builder()
            .accessTokenId(token.getAccessTokenId())
            .userId(token.getUserId())
            .lastAccess(dateTimeUtil.convertDomain(token.getLastAccess()))
            .characterId(token.getCharacterId())
            .build();
    }
}
