package com.github.saphyra.skyxplore.userdata.user.cache;

import com.github.saphyra.cache.AbstractCache;
import com.github.saphyra.skyxplore.userdata.user.UserQueryService;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

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
        return get(key, () -> Optional.of(userQueryService.isEmailExists(key)));
    }
}
