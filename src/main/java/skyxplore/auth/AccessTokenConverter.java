package skyxplore.auth;

import com.github.saphyra.authservice.domain.AccessToken;
import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.SkyXpAccessToken;

import java.util.Optional;

@Component
@RequiredArgsConstructor
//TODO unit test
public class AccessTokenConverter extends ConverterBase<SkyXpAccessToken, AccessToken> {
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

        SkyXpAccessToken entity = new SkyXpAccessToken();
        entity.setAccessTokenId(domain.getAccessTokenId());
        entity.setUserId(domain.getUserId());
        entity.setLastAccess(domain.getLastAccess());
        original.ifPresent(skyXpAccessToken -> entity.setCharacterId(skyXpAccessToken.getCharacterId()));
        return entity;
    }
}
