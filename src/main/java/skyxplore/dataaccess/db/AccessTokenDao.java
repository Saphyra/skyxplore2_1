package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.accesstoken.AccessTokenConverter;
import skyxplore.dataaccess.db.repository.AccessTokenRepository;

import java.util.Calendar;

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

    public void deleteExpired(Calendar expiration) {
        accessTokenRepository.deleteExpired(expiration);
    }

    public AccessToken findByUserId(String userId) {
        return accessTokenConverter.convertEntity(accessTokenRepository.findByUserId(userId));
    }

    public void save(AccessToken accessToken) {
        accessTokenRepository.save(accessTokenConverter.convertDomain(accessToken));
    }

    public void update(AccessToken token) {
        save(token);
    }
}