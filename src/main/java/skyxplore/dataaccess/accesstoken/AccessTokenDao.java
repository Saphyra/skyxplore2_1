package skyxplore.dataaccess.accesstoken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.service.domain.AccessToken;
import skyxplore.dataaccess.accesstoken.converter.AccessTokenConverter;
import skyxplore.dataaccess.accesstoken.repository.AccessTokenRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessTokenDao {
    private final AccessTokenRepository accessTokenRepository;
    private final AccessTokenConverter accessTokenConverter;

    public void save(AccessToken accessToken){
        accessTokenRepository.save(accessTokenConverter.convertDomain(accessToken));
    }

    public void delete(AccessToken accessToken){
        accessTokenRepository.deleteById(accessToken.getAccessTokenId());
    }

    public AccessToken findByUserId(Long userId){
        return accessTokenConverter.convertEntity(accessTokenRepository.findByUserId(userId));
    }

    public void update(AccessToken token){
        save(token);
    }

    public void deleteById(String accessTokenId){
        accessTokenRepository.deleteById(accessTokenId);
    }

    public void deleteByUserId(Long userId){
        log.info("Deleting accessToken of user {}", userId);
        accessTokenRepository.deleteByUserId(userId);
    }
}