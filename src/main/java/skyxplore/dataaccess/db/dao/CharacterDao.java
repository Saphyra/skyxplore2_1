package skyxplore.dataaccess.db.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.converter.CharacterConverter;
import skyxplore.dataaccess.db.entity.CharacterEntity;
import skyxplore.dataaccess.db.repository.CharacterRepository;
import skyxplore.exception.CharacterNotFoundException;
import skyxplore.service.domain.SkyXpCharacter;
import skyxplore.service.domain.SkyXpUser;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterDao {
    private final CharacterRepository characterRepository;
    private final UserDao userDao;
    private final CharacterConverter characterConverter;
    private final EquippedShipDao equippedShipDao;

    public void deleteById(String characterId){
        equippedShipDao.deleteByCharacterId(characterId);

        log.info("Deleting character {}", characterId);
        characterRepository.deleteById(characterId);
    }

    public void deleteByUserId(String userId){
        List<SkyXpCharacter> characters = findByUserId(userId);
        characters.forEach(e -> deleteById(e.getCharacterId()));
    }

    public SkyXpCharacter findById(String characterId){
        Optional<CharacterEntity> character = characterRepository.findById(characterId);
        if(character.isPresent()){
            return characterConverter.convertEntity(character.get());
        }
        throw new CharacterNotFoundException("No character found with id" + characterId);
    }

    public List<SkyXpCharacter> findByUserId(String userId){
        SkyXpUser user = userDao.findById(userId);
        return characterConverter.convertEntity(characterRepository.findByUserId(user.getUserId()));
    }

    public SkyXpCharacter findByCharacterName(String characterName){
        return characterConverter.convertEntity(characterRepository.findByCharacterName(characterName));
    }

    public SkyXpCharacter save(SkyXpCharacter character){
        return characterConverter.convertEntity(characterRepository.save(characterConverter.convertDomain(character)));
    }

    @Transactional
    public void renameCharacter(String characterId, String newCharacterName){
        Optional<CharacterEntity> character = characterRepository.findById(characterId);
        if(character.isPresent()){
            CharacterEntity entity = character.get();
            entity.setCharacterName(newCharacterName);
        }else{
            throw new CharacterNotFoundException("Character not found with id " + characterId);
        }
    }
}
