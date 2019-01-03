package skyxplore.dataaccess.db;

import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.AccessTokenRepository;
import skyxplore.domain.accesstoken.SkyXpAccessToken;
import skyxplore.domain.accesstoken.SkyXpAccessTokenConverter;
import skyxplore.domain.accesstoken.AccessTokenEntity;
import skyxplore.util.DateTimeUtil;

import java.time.OffsetDateTime;
import java.util.Optional;


@SuppressWarnings("WeakerAccess")
@Component
@Slf4j
public class AccessTokenDao extends AbstractDao<AccessTokenEntity, SkyXpAccessToken, String, AccessTokenRepository> {
    private final DateTimeUtil dateTimeUtil;

    public AccessTokenDao(SkyXpAccessTokenConverter converter, AccessTokenRepository repository, DateTimeUtil dateTimeUtil) {
        super(converter, repository);
        this.dateTimeUtil = dateTimeUtil;
    }

    public void deleteByUserId(String userId) {
        log.info("Deleting accessToken of user {}", userId);
        repository.deleteByUserId(userId);
    }

    public void deleteExpired(OffsetDateTime expiration) {
        repository.deleteExpired(dateTimeUtil.convertDomain(expiration));
    }

    public Optional<SkyXpAccessToken> findByCharacterId(String characterId) {
        return converter.convertEntityToOptional(repository.findByCharacterId(characterId));
    }

    public Optional<SkyXpAccessToken> findByUserId(String userId) {
        return converter.convertEntityToOptional(repository.findByUserId(userId));
    }

    public Optional<SkyXpAccessToken> findByUserIdOrTokenId(String userId, String tokenId) {
        return converter.convertEntityToOptional(repository.findByUserIdOrAccessTokenId(userId, tokenId));
    }
}
