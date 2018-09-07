package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.BlockedCharacterRepository;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.domain.community.blockedcharacter.BlockedCharacterConverter;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlockedCharacterDao {
    private final BlockedCharacterConverter blockedCharacterConverter;
    private final BlockedCharacterRepository blockedCharacterRepository;

    public void delete(BlockedCharacter blockedCharacter) {
        blockedCharacterRepository.delete(blockedCharacterConverter.convertDomain(blockedCharacter));
    }

    public void deleteByCharacterId(String characterId) {
        blockedCharacterRepository.deleteByCharacterId(characterId);
    }

    public BlockedCharacter findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId) {
        return blockedCharacterConverter.convertEntity(blockedCharacterRepository.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId));
    }

    public List<BlockedCharacter> findByCharacterIdOrBlockedCharacterId(String characterId, String blockedCharacterId) {
        return blockedCharacterConverter.convertEntity(blockedCharacterRepository.findByCharacterIdOrBlockedCharacterId(characterId, blockedCharacterId));
    }

    public List<BlockedCharacter> getBlockedCharactersOf(String characterId) {
        return blockedCharacterConverter.convertEntity(blockedCharacterRepository.findByCharacterId(characterId));
    }

    public void save(BlockedCharacter blockedCharacter) {
        blockedCharacterRepository.save(blockedCharacterConverter.convertDomain(blockedCharacter));
    }
}
