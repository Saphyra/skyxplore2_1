package com.github.saphyra.skyxplore.platform.auth;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.auth.domain.AccessToken;
import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AccessTokenConverter extends ConverterBase<SkyXpAccessToken, AccessToken> {
    private final AccessTokenDao accessTokenDao;

    @Override
    protected AccessToken processEntityConversion(SkyXpAccessToken entity) {
        return AccessToken.builder()
            .accessTokenId(entity.getAccessTokenId())
            .userId(entity.getUserId())
            .lastAccess(entity.getLastAccess())
            .isPersistent(false)
            .build();
    }

    @Override
    protected SkyXpAccessToken processDomainConversion(AccessToken domain) {
        Optional<SkyXpAccessToken> original = accessTokenDao.findByUserId(domain.getUserId());

        SkyXpAccessToken.SkyXpAccessTokenBuilder builder = SkyXpAccessToken.builder()
            .accessTokenId(domain.getAccessTokenId())
            .userId(domain.getUserId())
            .lastAccess(domain.getLastAccess());

        original.ifPresent(skyXpAccessToken -> builder.characterId(skyXpAccessToken.getCharacterId()));
        return builder.build();
    }
}
