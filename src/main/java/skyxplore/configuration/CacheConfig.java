package skyxplore.configuration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.UserFacade;
import skyxplore.service.character.CharacterQueryService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
@SuppressWarnings({"unused", "NullableProblems", "UnstableApiUsage"})
public class CacheConfig {

    @Bean(name = "emailCache")
    public Cache<String, Boolean> emailCache(UserFacade userFacade) {
        CacheLoader<String, Boolean> loader = new CacheLoader<String, Boolean>() {
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
    public Cache<String, Boolean> characterNameCache(CharacterQueryService characterQueryService) {
        CacheLoader<String, Boolean> loader = new CacheLoader<String, Boolean>() {
            @Override
            public Boolean load(String key) {
                return characterQueryService.isCharNameExists(key);
            }
        };

        return CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(loader);
    }

    @Bean(name = "characterNameLikeCache")
    public Cache<String, List<SkyXpCharacter>> characterNameCacheLikeCache(CharacterDao characterDao) {
        CacheLoader<String, List<SkyXpCharacter>> loader = new CacheLoader<String, List<SkyXpCharacter>>() {
            @Override
            public List<SkyXpCharacter> load(String s) throws Exception {
                return characterDao.findCharacterByNameLike(s);
            }
        };

        return CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(loader);
    }
}
