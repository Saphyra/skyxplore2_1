package com.github.saphyra.skyxplore.userdata.settings.locale;

import com.github.saphyra.cache.AbstractCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocaleCache extends AbstractCache<String, String> {
    private final LocaleService localeService;

    @Override
    //TODO unit test
    public Optional<String> get(String key) {
        return localeService.getLocale(key);
    }
}
