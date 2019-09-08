package com.github.saphyra.skyxplore.userdata.settings.locale;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.github.saphyra.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;

@Component
public class LocaleCache extends AbstractCache<String, String> {
    private final LocaleQueryService localeQueryService;

    public LocaleCache(LocaleQueryService localeQueryService) {
        super(CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build());
        this.localeQueryService = localeQueryService;
    }


    @Override
    public Optional<String> get(String key) {
        return localeQueryService.getLocale(key);
    }
}
