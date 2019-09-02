package com.github.saphyra.skyxplore.userdata.user.cache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.github.saphyra.cache.AbstractCache;
import com.github.saphyra.skyxplore.userdata.user.CredentialsService;
import com.google.common.cache.CacheBuilder;

@Component
//TODO unit test
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
