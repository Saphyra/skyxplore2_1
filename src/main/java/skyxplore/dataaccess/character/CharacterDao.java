package skyxplore.dataaccess.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.character.entity.converter.CharacterConverter;
import skyxplore.dataaccess.character.repository.CharacterRepository;
import skyxplore.dataaccess.user.entity.UserEntity;
import skyxplore.dataaccess.user.repository.UserRepository;
import skyxplore.exception.CharacterNameAlreadyExistsException;
import skyxplore.service.domain.SkyXpCharacter;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterDao {
    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;
    private final CharacterConverter characterConverter;

    public List<SkyXpCharacter> findByUserId(Long userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        return characterConverter.convertEntity(characterRepository.findByUser(user.get()));
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
}
