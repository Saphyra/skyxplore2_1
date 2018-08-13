package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.CharacterDeleteRequest;
import skyxplore.controller.request.CreateCharacterRequest;
import skyxplore.controller.request.RenameCharacterRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.character.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterFacade {
    private final BuyItemService buyItemService;
    private final CharacterCreatorService characterCreatorService;
    private final CharacterDeleteService characterDeleteService;
    private final CharacterQueryService characterQueryService;
    private final CharacterRenameService characterRenameService;

    public void buyItems(Map<String, Integer> items, String characterId, String userId) {
        buyItemService.buyItems(items, characterId, userId);
    }

    public void createCharacter(CreateCharacterRequest request, String userId) {
        characterCreatorService.createCharacter(request, userId);
    }

    public void deleteCharacter(CharacterDeleteRequest request, String userId) {
        characterDeleteService.deleteCharacter(request, userId);
    }

    public List<SkyXpCharacter> getCharactersByUserId(String userId) {
        return characterQueryService.getCharactersByUserId(userId);
    }

    public View<EquipmentViewList> getEquipmentsOfCharacter(String userId, String characterId) {
        return characterQueryService.getEquipmentsOfCharacter(userId, characterId);
    }

    public boolean isCharNameExists(String charName) {
        return characterQueryService.isCharNameExists(charName);
    }

    public Integer getMoneyOfCharacter(String userId, String characterId) {
        return characterQueryService.getMoneyOfCharacter(userId, characterId);
    }

    public void renameCharacter(RenameCharacterRequest request, String userId) {
        characterRenameService.renameCharacter(request, userId);
    }
}
