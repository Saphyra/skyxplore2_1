package skyxplore.domain.accesstoken;

import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.util.DateTimeUtil;

@Component
@RequiredArgsConstructor
public class AccessTokenConverter extends ConverterBase<AccessTokenEntity, AccessToken> {
    private final DateTimeUtil dateTimeUtil;


    @Override
    public AccessToken processEntityConversion(AccessTokenEntity entity) {
        if (entity == null) {
            return null;
        }
        AccessToken token = new AccessToken();
        token.setAccessTokenId(entity.getAccessTokenId());
        token.setUserId(entity.getUserId());
        token.setLastAccess(dateTimeUtil.convertEntity(entity.getLastAccess()));
        token.setCharacterId(entity.getCharacterId());
        return token;
    }

    @Override
    public AccessTokenEntity processDomainConversion(AccessToken token) {
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
