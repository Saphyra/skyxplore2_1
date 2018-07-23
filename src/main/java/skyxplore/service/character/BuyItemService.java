package skyxplore.service.character;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.exception.NotEnoughMoneyException;
import skyxplore.service.GameDataFacade;

@Slf4j
@RequiredArgsConstructor
@Service
public class BuyItemService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final GameDataFacade gameDataFacade;

    public void buyItems(Map<String, Integer> items, String characterId, String userId) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        Integer cost = validateMoney(items, character);

        character.spendMoney(cost);
        character.buyEquipments(items);
        log.info("Saving character {}", character);
        characterDao.save(character);
    }

    private Integer validateMoney(Map<String, Integer> items, SkyXpCharacter character) {
        Integer cost = countCost(items);
        if (cost > character.getMoney()) {
            throw new NotEnoughMoneyException(character.getCharacterId() + " wanted to buy items cost " + cost + ", while he had only " + character.getMoney());
        }
        return cost;
    }

    private Integer countCost(Map<String, Integer> items) {
        Set<String> keys = items.keySet();
        return keys.stream()
            .map(k -> gameDataFacade.findBuyable(k).getBuyPrice() * items.get(k))
            .reduce(0, (a, b) -> a + b);
    }
}
