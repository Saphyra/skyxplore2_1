package skyxplore.dataaccess.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.auth.domain.AccessToken;
import skyxplore.dataaccess.auth.converter.AccessTokenConverter;
import skyxplore.dataaccess.auth.repository.AccessTokenRepository;

@Component
@RequiredArgsConstructor
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
}
