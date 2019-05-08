package com.github.saphyra.skyxplore.character.cache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.github.saphyra.cache.AbstractCache;
import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.google.common.cache.CacheBuilder;

@Component
public class CharacterNameCache extends AbstractCache<String, Boolean> {
    private final CharacterQueryService characterQueryService;

    public CharacterNameCache(CharacterQueryService characterQueryService) {
        super(CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build()
        );
        this.characterQueryService = characterQueryService;
    }

    @Override
    public Optional<Boolean> get(String key) {
        return get(key, () -> Optional.of(characterQueryService.isCharNameExists(key)));
    }
}
