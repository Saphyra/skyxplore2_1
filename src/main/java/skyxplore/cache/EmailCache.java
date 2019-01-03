package skyxplore.cache;

import com.github.saphyra.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
import skyxplore.service.user.UserQueryService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class EmailCache extends AbstractCache<String, Boolean> {
    private final UserQueryService userQueryService;

    public EmailCache(UserQueryService userQueryService) {
        super(CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build()
        );
        this.userQueryService = userQueryService;
    }

    @Override
    public Optional<Boolean> get(String key) {
        return get(key, () -> userQueryService.isEmailExists(key));
    }
}
