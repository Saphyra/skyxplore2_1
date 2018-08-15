package skyxplore.domain.accesstoken;

import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
//TODO unit test
public class AccessTokenConverter extends ConverterBase<AccessTokenEntity, AccessToken> {

    @Override
    public AccessToken convertEntity(AccessTokenEntity entity) {
        if (entity == null) {
            return null;
        }
        AccessToken token = new AccessToken();
        token.setAccessTokenId(entity.getAccessTokenId());
        token.setUserId(entity.getUserId());
        token.setLastAccess(LocalDateTime.ofEpochSecond(entity.getLastAccess(), 0, ZoneOffset.UTC));
        return token;
    }

    @Override
    public AccessTokenEntity convertDomain(AccessToken token) {
        if (token == null) {
            throw new IllegalArgumentException("token must not be null");
        }
        AccessTokenEntity entity = new AccessTokenEntity();
        entity.setAccessTokenId(token.getAccessTokenId());
        entity.setUserId(token.getUserId());
        entity.setLastAccess(token.getLastAccess().toEpochSecond(ZoneOffset.UTC));
        return entity;
    }
}
