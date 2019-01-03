package skyxplore.cache;

import com.github.saphyra.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
import skyxplore.service.character.CharacterQueryService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        return get(key, () -> characterQueryService.isCharNameExists(key));
    }
}
