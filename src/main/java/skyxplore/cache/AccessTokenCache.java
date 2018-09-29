package skyxplore.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
import skyxplore.cache.base.AbstractCache;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class AccessTokenCache extends AbstractCache<String, Optional<AccessToken>> {
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
        return get(key, () -> Optional.ofNullable(accessTokenDao.findByUserId(key)));
    }
}
