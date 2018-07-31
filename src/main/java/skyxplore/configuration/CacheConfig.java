package skyxplore.configuration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.UserFacade;
import skyxplore.service.character.CharacterQueryService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
@SuppressWarnings({"unused", "NullableProblems"})
public class CacheConfig {
    @Bean(name = "accessTokenCache")
    public Cache<String, Optional<AccessToken>> accessTokenCache(AccessTokenDao accessTokenDao){
        CacheLoader<String, Optional<AccessToken>> loader;
        loader = new CacheLoader<String, Optional<AccessToken>>() {
            @Override
            public Optional<AccessToken> load(String key) {
                AccessToken accessToken = accessTokenDao.findByUserId(key);
                return Optional.ofNullable(accessToken);
            }
        };

        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(loader);
    }

    @Bean(name = "userNameCache")
    public Cache<String, Boolean> userNameCache(UserFacade userFacade) {
        CacheLoader<String, Boolean> loader;
        loader = new CacheLoader<String, Boolean>() {
            @Override
            public Boolean load(String key) {
                return userFacade.isUserNameExists(key);
            }
        };

        return CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(loader);
    }

    @Bean(name = "emailCache")
    public Cache<String, Boolean> emailCache(UserFacade userFacade) {
        CacheLoader<String, Boolean> loader;
        loader = new CacheLoader<String, Boolean>() {
            @Override
            public Boolean load(String key) {
                return userFacade.isEmailExists(key);
            }
        };

        return CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(loader);
    }

    @Bean(name = "characterNameCache")
    public Cache<String, Boolean> characterNameCache(CharacterQueryService characterQueryService){
        CacheLoader<String, Boolean> loader;
        loader = new CacheLoader<String, Boolean>() {
            @Override
            public Boolean load(String key) {
                return characterQueryService.isCharNameExists(key);
            }
        };

        return CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(loader);
    }
}
