package skyxplore.service.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.view.View;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.exception.InvalidAccessException;
import skyxplore.service.GameDataFacade;

import java.util.List;

@SuppressWarnings("WeakerAccess")
@Slf4j
@RequiredArgsConstructor
@Service
public class CharacterQueryService {
    private final CharacterDao characterDao;
    private final GameDataFacade gameDataFacade;

    public SkyXpCharacter findCharacterByIdAuthorized(String characterId, String userId) {
        SkyXpCharacter character = characterDao.findById(characterId);
        if (!userId.equals(character.getUserId())) {
            throw new InvalidAccessException("Unauthorized character access. CharacterId: " + character.getCharacterId() + ", userId: " + userId);
        }
        return character;
    }

    public List<SkyXpCharacter> findCharacterByNameLike(String name) {
        return characterDao.findCharacterByNameLike(name);
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
