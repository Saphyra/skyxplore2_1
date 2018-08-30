package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.character.CharacterConverter;
import skyxplore.domain.character.CharacterEntity;
import skyxplore.dataaccess.db.repository.CharacterRepository;
import skyxplore.exception.CharacterNotFoundException;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.user.SkyXpUser;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterDao {
    private final CharacterConverter characterConverter;
    private final CharacterRepository characterRepository;
    private final EquippedShipDao equippedShipDao;
    private final FactoryDao factoryDao;
    private final UserDao userDao;

    public void deleteById(String characterId) {
        equippedShipDao.deleteByCharacterId(characterId);
        factoryDao.deleteByCharacterId(characterId);

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
        Optional<CharacterEntity> character = characterRepository.findById(characterId);
        return character.map(characterConverter::convertEntity).orElse(null);
    }

    public List<SkyXpCharacter> findByUserId(String userId) {
        SkyXpUser user = userDao.findById(userId);
        return characterConverter.convertEntity(characterRepository.findByUserId(user.getUserId()));
    }

    @Transactional
    public void renameCharacter(String characterId, String newCharacterName) {
        Optional<CharacterEntity> character = characterRepository.findById(characterId);
        if (character.isPresent()) {
            CharacterEntity entity = character.get();
            entity.setCharacterName(newCharacterName);
        } else {
            throw new CharacterNotFoundException("Character not found with id " + characterId);
        }
    }

    public SkyXpCharacter save(SkyXpCharacter character) {
        return characterConverter.convertEntity(characterRepository.save(characterConverter.convertDomain(character)));
    }
}
