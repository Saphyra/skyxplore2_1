package skyxplore.dataaccess.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.character.entity.CharacterEntity;
import skyxplore.dataaccess.character.entity.converter.CharacterConverter;
import skyxplore.dataaccess.character.repository.CharacterRepository;
import skyxplore.dataaccess.user.entity.UserEntity;
import skyxplore.dataaccess.user.repository.UserRepository;
import skyxplore.exception.CharacterNameAlreadyExistsException;
import skyxplore.exception.CharacterNotFoundException;
import skyxplore.exception.UserNotFoundException;
import skyxplore.service.domain.SkyXpCharacter;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterDao {
    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;
    private final CharacterConverter characterConverter;

    public void deleteById(Long characterId){
        characterRepository.deleteById(characterId);
    }

    public SkyXpCharacter findById(Long characterId){
        Optional<CharacterEntity> character = characterRepository.findById(characterId);
        if(character.isPresent()){
            return characterConverter.convertEntity(character.get());
        }
        throw new CharacterNotFoundException("No character found with id" + characterId);
    }

    public List<SkyXpCharacter> findByUserId(Long userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isPresent()){
            return characterConverter.convertEntity(characterRepository.findByUser(user.get()));
        }
        throw new UserNotFoundException("No user found with id " + userId);
    }

    public SkyXpCharacter findByCharacterName(String characterName){
        return characterConverter.convertEntity(characterRepository.findByCharacterName(characterName));
    }

    public SkyXpCharacter save(SkyXpCharacter character){
        if(findByCharacterName(character.getCharacterName()) != null){
            throw new CharacterNameAlreadyExistsException("Character already exists with name " + character.getCharacterName());
        }
        return characterConverter.convertEntity(characterRepository.save(characterConverter.convertDomain(character)));
    }

    @Transactional
    public void renameCharacter(Long characterId, String newCharacterName){
        Optional<CharacterEntity> character = characterRepository.findById(characterId);
        if(character.isPresent()){
            CharacterEntity entity = character.get();
            entity.setCharacterName(newCharacterName);
        }else{
            throw new CharacterNotFoundException("Character not found with id " + characterId);
        }
    }
}
