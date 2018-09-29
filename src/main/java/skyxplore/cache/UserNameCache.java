package skyxplore.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
import skyxplore.cache.base.AbstractCache;
import skyxplore.service.credentials.CredentialsService;

import java.util.concurrent.TimeUnit;

@Component
public class UserNameCache extends AbstractCache<String, Boolean> {
    private final CredentialsService credentialsService;

    public UserNameCache(CredentialsService credentialsService) {
        super(CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build()
        );
        this.credentialsService = credentialsService;
    }

    @Override
    public Boolean get(String key) {
        return get(key, () -> credentialsService.isUserNameExists(key));
    }
}
