package org.github.saphyra.skyxplore.user.cache;

import com.github.saphyra.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.user.CredentialsService;

import java.util.Optional;
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
    public Optional<Boolean> get(String key) {
        return get(key, () -> Optional.of(credentialsService.isUserNameExists(key)));
    }
}
