package skyxplore.dataaccess.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.AccessTokenRepository;
import com.github.saphyra.converter.Converter;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.accesstoken.AccessTokenEntity;


@SuppressWarnings("WeakerAccess")
@Component
@Slf4j
public class AccessTokenDao extends AbstractDao<AccessTokenEntity, AccessToken, String, AccessTokenRepository>{

    public AccessTokenDao(Converter<AccessTokenEntity, AccessToken> converter, AccessTokenRepository repository) {
        super(converter, repository);
    }

    public void deleteByUserId(String userId) {
        log.info("Deleting accessToken of user {}", userId);
        repository.deleteByUserId(userId);
    }

    public void deleteExpired(Long expiration) {
        repository.deleteExpired(expiration);
    }

    public AccessToken findByCharacterId(String characterId) {
        return converter.convertEntity(repository.findByCharacterId(characterId));
    }

    public AccessToken findByUserId(String userId) {
        return converter.convertEntity(repository.findByUserId(userId));
    }

    public AccessToken findByUserIdOrTokenId(String userId, String tokenId) {
        return converter.convertEntity(repository.findByUserIdOrAccessTokenId(userId, tokenId));
    }
}
