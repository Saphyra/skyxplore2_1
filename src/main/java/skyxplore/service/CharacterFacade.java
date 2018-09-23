package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.controller.request.character.CharacterDeleteRequest;
import skyxplore.controller.request.character.CreateCharacterRequest;
import skyxplore.controller.request.character.RenameCharacterRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.character.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterFacade {
    private final BuyItemService buyItemService;
    private final CharacterCreatorService characterCreatorService;
    private final CharacterDeleteService characterDeleteService;
    private final CharacterQueryService characterQueryService;
    private final CharacterRenameService characterRenameService;

    public void buyItems(Map<String, Integer> items, String characterId) {
        buyItemService.buyItems(items, characterId);
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

    public View<EquipmentViewList> getEquipmentsOfCharacter(String characterId) {
        return characterQueryService.getEquipmentsOfCharacter(characterId);
    }

    public Integer getMoneyOfCharacter(String characterId) {
        return characterQueryService.getMoneyOfCharacter(characterId);
    }

    public void renameCharacter(RenameCharacterRequest request, String userId) {
        characterRenameService.renameCharacter(request, userId);
    }
}
