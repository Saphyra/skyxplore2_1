package org.github.saphyra.skyxplore.character;

import java.util.List;
import java.util.Map;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.domain.request.CreateCharacterRequest;
import org.github.saphyra.skyxplore.character.domain.request.RenameCharacterRequest;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.service.accesstoken.CharacterSelectService;
import skyxplore.service.character.CharacterDeleteService;
import skyxplore.service.character.CharacterRenameService;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterFacade {
    private final BuyItemService buyItemService;
    private final CharacterCreatorService characterCreatorService;
    private final CharacterDeleteService characterDeleteService;
    private final CharacterQueryService characterQueryService;
    private final CharacterRenameService characterRenameService;
    private final CharacterSelectService characterSelectService;

    public void buyItems(Map<String, Integer> items, String characterId) {
        buyItemService.buyItems(items, characterId);
    }

    public SkyXpCharacter createCharacter(CreateCharacterRequest request, String userId) {
        return characterCreatorService.createCharacter(request, userId);
    }

    public void deleteCharacter(String characterId, String userId) {
        characterDeleteService.deleteCharacter(characterId, userId);
    }

    public List<SkyXpCharacter> getCharactersByUserId(String userId) {
        return characterQueryService.getCharactersByUserId(userId);
    }

    public List<String> getEquipmentsOfCharacter(String characterId) {
        return characterQueryService.getEquipmentsOfCharacter(characterId);
    }

    public Integer getMoneyOfCharacter(String characterId) {
        return characterQueryService.getMoneyOfCharacter(characterId);
    }

    public SkyXpCharacter renameCharacter(RenameCharacterRequest request, String userId) {
        return characterRenameService.renameCharacter(request, userId);
    }

    public void selectCharacter(String characterId, String userId) {
        characterSelectService.selectCharacter(characterId, userId);
    }
}