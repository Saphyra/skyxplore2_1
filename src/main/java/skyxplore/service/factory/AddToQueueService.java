package skyxplore.service.factory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.domain.product.Product;
import skyxplore.exception.NotEnoughMaterialsException;
import skyxplore.exception.NotEnoughMoneyException;
import skyxplore.service.GameDataFacade;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.IdGenerator;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddToQueueService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final FactoryDao factoryDao;
    private final FactoryQueryService factoryQueryService;
    private final GameDataFacade gameDataFacade;
    private final IdGenerator idGenerator;
    private final ProductDao productDao;

    @Transactional
    public void addToQueue(String userId, String characterId, AddToQueueRequest request) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        Factory factory = factoryQueryService.findFactoryOfCharacterValidated(characterId);
        FactoryData elementData = gameDataFacade.getFactoryData(request.getElementId());

        int price = elementData.getBuildPrice() * request.getAmount();
        validateMoney(character.getMoney(), price);
        validateMaterials(factory.getMaterials(), elementData, request.getAmount());

        Product product = createProduct(factory.getFactoryId(), elementData, request.getAmount());
        character.spendMoney(price);
        removeMaterials(factory.getMaterials(), elementData, request.getAmount());

        characterDao.save(character);
        factoryDao.save(factory);
        productDao.save(product);
    }

    private void validateMoney(int actual, int price) {
        if (actual < price) {
            throw new NotEnoughMoneyException("Not enough money. Needed: " + price + ", have: " + actual);
        }
    }

    private void validateMaterials(Materials materials, FactoryData elementData, int amount) {
        Map<String, Integer> requiredMaterials = elementData.getMaterials();
        Set<String> keys = requiredMaterials.keySet();
        for (String key : keys) {
            int required = requiredMaterials.get(key) * amount;
            if (materials.get(key) < required) {
                throw new NotEnoughMaterialsException("Not enough " + key + ". Needed: " + required + ", have: " + materials.get(key));
            }
        }
    }

    private Product createProduct(String factoryId, FactoryData elementData, Integer amount) {
        return Product.builder()
            .productId(idGenerator.getRandomId())
            .factoryId(factoryId)
            .elementId(elementData.getId())
            .amount(amount)
            .constructionTime(elementData.getConstructionTime() * amount)
            .addedAt(LocalDateTime.now(ZoneOffset.UTC).toEpochSecond(ZoneOffset.UTC))
            .build();
    }

    private void removeMaterials(Materials materials, FactoryData elementData, Integer amount) {
        elementData.getMaterials().keySet()
            .forEach(k -> materials.removeMaterial(
                k,
                elementData.getMaterials().get(k) * amount)
            );
    }
}
