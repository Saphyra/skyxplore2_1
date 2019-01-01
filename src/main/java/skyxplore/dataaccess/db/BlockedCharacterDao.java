package skyxplore.dataaccess.db;

import com.github.saphyra.converter.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.BlockedCharacterRepository;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.domain.community.blockedcharacter.BlockedCharacterEntity;

import java.util.List;

@Component
@Slf4j
public class BlockedCharacterDao extends AbstractDao<BlockedCharacterEntity, BlockedCharacter, Long, BlockedCharacterRepository> {
    public BlockedCharacterDao(Converter<BlockedCharacterEntity, BlockedCharacter> converter, BlockedCharacterRepository repository) {
        super(converter, repository);
    }

    public void deleteByCharacterId(String characterId) {
        repository.deleteByCharacterId(characterId);
    }

    public BlockedCharacter findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId) {
        return converter.convertEntity(repository.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId));
    }

    public List<BlockedCharacter> findByCharacterIdOrBlockedCharacterId(String characterId, String blockedCharacterId) {
        return converter.convertEntity(repository.findByCharacterIdOrBlockedCharacterId(characterId, blockedCharacterId));
    }

    public List<BlockedCharacter> getBlockedCharactersOf(String characterId) {
        return converter.convertEntity(repository.findByCharacterId(characterId));
    }
}
