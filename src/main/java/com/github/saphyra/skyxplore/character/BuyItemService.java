package com.github.saphyra.skyxplore.character;

import java.util.Map;

import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
class BuyItemService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final GameDataFacade gameDataFacade;

    void buyItems(Map<String, Integer> items, String characterId) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        Integer cost = countCost(items);
        character.buyEquipments(items, cost);
        characterDao.save(character);
    }

    private Integer countCost(Map<String, Integer> items) {
        return items.keySet().stream()
            .map(k -> gameDataFacade.findBuyable(k).getBuyPrice() * items.get(k))
            .reduce(0, (a, b) -> a + b);
    }
}
