package org.github.saphyra.skyxplore.community.blockedcharacter.repository;

import java.util.List;
import java.util.Optional;

import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BlockedCharacterDao extends AbstractDao<BlockedCharacterEntity, BlockedCharacter, Long, BlockedCharacterRepository> {
    public BlockedCharacterDao(Converter<BlockedCharacterEntity, BlockedCharacter> converter, BlockedCharacterRepository repository) {
        super(converter, repository);
    }

    public void deleteByCharacterId(String characterId) {
        repository.deleteByCharacterId(characterId);
    }

    public Optional<BlockedCharacter> findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId) {
        return converter.convertEntity(repository.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId));
    }

    public List<BlockedCharacter> findByCharacterIdOrBlockedCharacterId(String characterId, String blockedCharacterId) {
        return converter.convertEntity(repository.findByCharacterIdOrBlockedCharacterId(characterId, blockedCharacterId));
    }

    public List<BlockedCharacter> getBlockedCharactersOf(String characterId) {
        return converter.convertEntity(repository.findByCharacterId(characterId));
    }
}
