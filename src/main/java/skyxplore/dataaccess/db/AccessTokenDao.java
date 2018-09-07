package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.AccessTokenRepository;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.accesstoken.AccessTokenConverter;


@SuppressWarnings("WeakerAccess")
@Component
@RequiredArgsConstructor
@Slf4j
public class AccessTokenDao {
    private final AccessTokenRepository accessTokenRepository;
    private final AccessTokenConverter accessTokenConverter;

    public void delete(AccessToken accessToken) {
        accessTokenRepository.deleteById(accessToken.getAccessTokenId());
    }

    public void deleteById(String accessTokenId) {
        accessTokenRepository.deleteById(accessTokenId);
    }

    public void deleteByUserId(String userId) {
        log.info("Deleting accessToken of user {}", userId);
        accessTokenRepository.deleteByUserId(userId);
    }

    public void deleteExpired(Long expiration) {
        accessTokenRepository.deleteExpired(expiration);
    }

    public AccessToken findByCharacterId(String characterId) {
        return accessTokenConverter.convertEntity(accessTokenRepository.findByCharacterId(characterId));
    }

    public AccessToken findByTokenId(String tokenId) {
        return accessTokenRepository.findById(tokenId)
            .map(accessTokenConverter::convertEntity)
            .orElse(null);
    }

    public AccessToken findByUserId(String userId) {
        return accessTokenConverter.convertEntity(accessTokenRepository.findByUserId(userId));
    }

    public AccessToken findByUserIdOrTokenId(String userId, String tokenId) {
        return accessTokenConverter.convertEntity(accessTokenRepository.findByUserIdOrAccessTokenId(userId, tokenId));
    }

    public void save(AccessToken accessToken) {
        accessTokenRepository.save(accessTokenConverter.convertDomain(accessToken));
    }
}
