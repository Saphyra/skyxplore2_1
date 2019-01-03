package skyxplore.cache;

import com.github.saphyra.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class AccessTokenCache extends AbstractCache<String, AccessToken> {
    private final AccessTokenDao accessTokenDao;

    public AccessTokenCache(AccessTokenDao accessTokenDao) {
        super(
            CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build()
        );
        this.accessTokenDao = accessTokenDao;
    }

    @Override
    public Optional<AccessToken> get(String key) {
        return get(key, () -> accessTokenDao.findByUserId(key));
    }
}
