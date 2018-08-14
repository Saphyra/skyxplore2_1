package skyxplore.service.character;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.view.View;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.friend.blockeduser.BlockedCharacter;
import skyxplore.exception.CharacterNotFoundException;
import skyxplore.exception.InvalidAccessException;
import skyxplore.service.GameDataFacade;
import skyxplore.service.community.BlockedCharacterQueryService;

@SuppressWarnings("WeakerAccess")
@Slf4j
@RequiredArgsConstructor
@Service
public class CharacterQueryService {
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterDao characterDao;
    private final GameDataFacade gameDataFacade;
    private final Cache<String, List<SkyXpCharacter>> characterNameLikeCache;

    public SkyXpCharacter findByCharacterId(String characterId) {
        SkyXpCharacter character = characterDao.findById(characterId);
        if (character == null) {
            throw new CharacterNotFoundException("Character not found with id " + characterId);
        }
        return character;
    }

    public SkyXpCharacter findCharacterByIdAuthorized(String characterId, String userId) {
        SkyXpCharacter character = characterDao.findById(characterId);
        if (!userId.equals(character.getUserId())) {
            throw new InvalidAccessException("Unauthorized character access. CharacterId: " + character.getCharacterId() + ", userId: " + userId);
        }
        return character;
    }

    public List<SkyXpCharacter> getCharactersCanBeBlocked(String name, String characterId, String userId) {
        SkyXpCharacter character = findCharacterByIdAuthorized(characterId, userId);
        List<SkyXpCharacter> characters = getCharactersOfNameLike(name);
        return characters.
            stream()
            .filter(c -> isOwnCharacter(c, userId)) //Filtering own characters
            .filter(c -> isBlocked(character, c))  //Filtering characters blocked by the character
            .filter(c -> isBlocked(c, character))  //Filtering characters that blocks the character
            .collect(Collectors.toList());
    }

    public List<SkyXpCharacter> getCharactersCanBeFriend(String name, String characterId, String userId) {
        SkyXpCharacter character = findCharacterByIdAuthorized(characterId, userId);
        List<SkyXpCharacter> characters = getCharactersOfNameLike(name);

        return characters.
            stream()
            .filter(c -> isOwnCharacter(c, userId)) //Filtering own characters
            .filter(c -> isBlocked(character, c))  //Filtering characters blocked by the character
            .filter(c -> isBlocked(c, character))  //Filtering characters that blocks the character
            .collect(Collectors.toList());
    }

    private List<SkyXpCharacter> getCharactersOfNameLike(String name) {
        try {
            return characterNameLikeCache.get(name);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private boolean isOwnCharacter(SkyXpCharacter character, String userId) {
        return !character.getUserId().equals(userId);
    }

    private boolean isBlocked(SkyXpCharacter character, SkyXpCharacter ownedCharacter) {
        List<BlockedCharacter> blockedCharacters = blockedCharacterQueryService.getBlockedCharactersOf(character.getCharacterId());
        return blockedCharacters.stream().anyMatch(blocked -> blocked.getBlockedCharacterId().equals(ownedCharacter.getCharacterId()));
    }

    public List<SkyXpCharacter> getCharactersByUserId(String userId) {
        return characterDao.findByUserId(userId);
    }

    public View<EquipmentViewList> getEquipmentsOfCharacter(String userId, String characterId) {
        SkyXpCharacter character = findCharacterByIdAuthorized(characterId, userId);

        return new View<>(
            new EquipmentViewList(character.getEquipments()),
            gameDataFacade.collectEquipmentData(character.getEquipments())
        );
    }

    public Integer getMoneyOfCharacter(String userId, String characterId) {
        SkyXpCharacter character = findCharacterByIdAuthorized(characterId, userId);
        return character.getMoney();
    }

    public boolean isCharNameExists(String charName) {
        return characterDao.findByCharacterName(charName) != null;
    }
}
