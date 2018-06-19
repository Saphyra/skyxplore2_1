package skyxplore.domain.accesstoken;

import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

@Component
public class AccessTokenConverter extends ConverterBase<AccessTokenEntity, AccessToken> {

    @Override
    public AccessToken convertEntity(AccessTokenEntity entity){
        if(entity == null){
            return null;
        }
        AccessToken token = new AccessToken();
        token.setAccessTokenId(entity.getAccessTokenId());
        token.setUserId(entity.getUserId());
        token.setLastAccess(entity.getLastAccess());
        return token;
    }

    @Override
    public AccessTokenEntity convertDomain(AccessToken token){
        if(token == null){
            throw new IllegalArgumentException("token must not be null");
        }
        AccessTokenEntity entity = new AccessTokenEntity();
        entity.setAccessTokenId(token.getAccessTokenId());
        entity.setUserId(token.getUserId());
        entity.setLastAccess(token.getLastAccess());
        return entity;
    }
}
