package skyxplore.cache;

import com.github.saphyra.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CharacterNameLikeCache extends AbstractCache<String, List<SkyXpCharacter>> {
    private final CharacterDao characterDao;

    public CharacterNameLikeCache(CharacterDao characterDao) {
        super(
            CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build()
        );
        this.characterDao = characterDao;
    }


    @Override
    public Optional<List<SkyXpCharacter>> get(String key) {
        return get(key, () -> Optional.of(characterDao.findCharacterByNameLike(key)));
    }
}
