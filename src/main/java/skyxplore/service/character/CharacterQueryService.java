package skyxplore.service.character;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.view.View;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.exception.InvalidAccessException;
import skyxplore.service.GameDataFacade;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
@Slf4j
@RequiredArgsConstructor
@Service
public class CharacterQueryService {
    private final CharacterDao characterDao;
    private final GameDataFacade gameDataFacade;
    private final Cache<String, List<SkyXpCharacter>> characterNameLikeCache;

    public SkyXpCharacter findCharacterByIdAuthorized(String characterId, String userId) {
        SkyXpCharacter character = characterDao.findById(characterId);
        if (!userId.equals(character.getUserId())) {
            throw new InvalidAccessException("Unauthorized character access. CharacterId: " + character.getCharacterId() + ", userId: " + userId);
        }
        return character;
    }

    public List<SkyXpCharacter> getBlockableCharacters(String name, String characterId, String userId) {
        SkyXpCharacter character = findCharacterByIdAuthorized(characterId, userId);
        List<SkyXpCharacter> characters = getCharactersOfNameLike(name);

        return characters.
            stream()
            .filter(c -> isOwnCharacter(c, character, userId))
            .collect(Collectors.toList());
    }

    public List<SkyXpCharacter> getCharactersCanBeFriend(String name, String characterId, String userId) {
        SkyXpCharacter character = findCharacterByIdAuthorized(characterId, userId);
        List<SkyXpCharacter> characters = getCharactersOfNameLike(name);

        return characters.
            stream()
            .filter(c -> isOwnCharacter(c, character, userId))
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

    private boolean isOwnCharacter(SkyXpCharacter friend, SkyXpCharacter character, String userId) {
        return !friend.getUserId().equals(userId);
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
