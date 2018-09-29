package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.BlockedCharacterDao;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
//TODO unit test
public class BlockedCharacterQueryService {
    private final BlockedCharacterDao blockedCharacterDao;

    public BlockedCharacter findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId) {
        return blockedCharacterDao.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId);
    }

    public List<BlockedCharacter> findByCharacterIdOrBlockedCharacterId(String characterId, String blockedCharacterId) {
        return blockedCharacterDao.findByCharacterIdOrBlockedCharacterId(characterId, blockedCharacterId);
    }

    public List<BlockedCharacter> getBlockedCharactersOf(String characterId) {
        return blockedCharacterDao.getBlockedCharactersOf(characterId);
    }
}
