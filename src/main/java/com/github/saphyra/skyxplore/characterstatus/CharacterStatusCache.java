package com.github.saphyra.skyxplore.characterstatus;

import com.github.saphyra.cache.AbstractCache;
import com.github.saphyra.skyxplore.characterstatus.domain.CharacterStatus;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class CharacterStatusCache extends AbstractCache<String, CharacterStatus> {
    private final CharacterStatusQueryService characterStatusQueryService;

    public CharacterStatusCache(CharacterStatusQueryService characterStatusQueryService) {
        super(CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.SECONDS).build());
        this.characterStatusQueryService = characterStatusQueryService;
    }

    @Override
    public Optional<CharacterStatus> get(String key) {
        return Optional.ofNullable(characterStatusQueryService.getCharacterStatus(key));
    }
}
