package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.CharacterRepository;
import skyxplore.domain.character.CharacterConverter;
import skyxplore.domain.character.SkyXpCharacter;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterDao {
    private final BlockedCharacterDao blockedCharacterDao;
    private final CharacterConverter characterConverter;
    private final CharacterRepository characterRepository;
    private final EquippedShipDao equippedShipDao;
    private final FactoryDao factoryDao;
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;
    private final MailDao mailDao;

    public void deleteById(String characterId) {
        equippedShipDao.deleteByCharacterId(characterId);
        factoryDao.deleteByCharacterId(characterId);
        friendRequestDao.deleteByCharacterId(characterId);
        friendshipDao.deleteByCharacterId(characterId);
        blockedCharacterDao.deleteByCharacterId(characterId);
        mailDao.deleteByCharacterId(characterId);

        log.info("Deleting character {}", characterId);
        characterRepository.deleteById(characterId);
    }

    public void deleteByUserId(String userId) {
        List<SkyXpCharacter> characters = findByUserId(userId);
        characters.forEach(e -> deleteById(e.getCharacterId()));
    }

    public SkyXpCharacter findByCharacterName(String characterName) {
        return characterConverter.convertEntity(characterRepository.findByCharacterName(characterName));
    }

    public List<SkyXpCharacter> findCharacterByNameLike(String name) {
        return characterConverter.convertEntity(characterRepository.findByCharacterNameContaining(name));
    }

    public SkyXpCharacter findById(String characterId) {
        return characterRepository.findById(characterId).map(characterConverter::convertEntity).orElse(null);
    }

    public List<SkyXpCharacter> findByUserId(String userId) {
        return characterConverter.convertEntity(characterRepository.findByUserId(userId));
    }

    public SkyXpCharacter save(SkyXpCharacter character) {
        return characterConverter.convertEntity(characterRepository.save(characterConverter.convertDomain(character)));
    }
}
