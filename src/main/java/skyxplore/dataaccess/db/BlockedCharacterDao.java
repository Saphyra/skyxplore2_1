package skyxplore.dataaccess.db;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.repository.BlockedCharacterRepository;
import skyxplore.domain.friend.blockeduser.BlockedCharacter;
import skyxplore.domain.friend.blockeduser.BlockedCharacterConverter;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlockedCharacterDao {
    private final BlockedCharacterRepository blockedCharacterRepository;
    private final BlockedCharacterConverter blockedCharacterConverter;

    public BlockedCharacter findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId) {
        return blockedCharacterConverter.convertEntity(blockedCharacterRepository.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId));
    }

    public BlockedCharacter save(BlockedCharacter blockedCharacter) {
        return blockedCharacterConverter.convertEntity(
            blockedCharacterRepository.save(
                blockedCharacterConverter.convertDomain(blockedCharacter)
            )
        );
    }

    public List<BlockedCharacter> getBlockedCharactersOf(String characterId) {
        return blockedCharacterConverter.convertEntity(blockedCharacterRepository.findByCharacterId(characterId));
    }
}
