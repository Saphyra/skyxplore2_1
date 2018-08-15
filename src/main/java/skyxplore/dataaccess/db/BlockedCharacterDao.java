package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.BlockedCharacterRepository;
import skyxplore.domain.community.blockeduser.BlockedCharacter;
import skyxplore.domain.community.blockeduser.BlockedCharacterConverter;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class BlockedCharacterDao {
    private final BlockedCharacterRepository blockedCharacterRepository;
    private final BlockedCharacterConverter blockedCharacterConverter;

    public void delete(BlockedCharacter blockedCharacter){
        blockedCharacterRepository.delete(blockedCharacterConverter.convertDomain(blockedCharacter));
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

    public BlockedCharacter save(BlockedCharacter blockedCharacter) {
        return blockedCharacterConverter.convertEntity(
            blockedCharacterRepository.save(
                blockedCharacterConverter.convertDomain(blockedCharacter)
            )
        );
    }
}
