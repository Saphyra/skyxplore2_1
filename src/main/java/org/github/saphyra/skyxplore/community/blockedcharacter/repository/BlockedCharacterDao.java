package org.github.saphyra.skyxplore.community.blockedcharacter.repository;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.event.CharacterDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class BlockedCharacterDao extends AbstractDao<BlockedCharacterEntity, BlockedCharacter, Long, BlockedCharacterRepository> {
    public BlockedCharacterDao(Converter<BlockedCharacterEntity, BlockedCharacter> converter, BlockedCharacterRepository repository) {
        super(converter, repository);
    }

    @EventListener
    void characterDeletedEventProcessor(CharacterDeletedEvent event) {
        repository.deleteByCharacterId(event.getCharacterId());
    }

    public Optional<BlockedCharacter> findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId) {
        return converter.convertEntity(repository.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId));
    }

    public List<BlockedCharacter> getByCharacterIdOrBlockedCharacterId(String characterId, String blockedCharacterId) {
        return converter.convertEntity(repository.getByCharacterIdOrBlockedCharacterId(characterId, blockedCharacterId));
    }

    public List<BlockedCharacter> getBlockedCharacters(String characterId) {
        return converter.convertEntity(repository.getByCharacterId(characterId));
    }
}
