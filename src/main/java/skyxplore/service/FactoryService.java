package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.domain.product.Product;
import skyxplore.exception.FactoryNotFoundException;
import skyxplore.exception.NotEnoughMaterialsException;
import skyxplore.exception.NotEnoughMoneyException;
import skyxplore.controller.request.AddToQueueRequest;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.util.IdGenerator;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryService {
    private final CharacterDao characterDao;
    private final CharacterService characterService;
    private final FactoryDao factoryDao;
    private final GameDataService gameDataService;
    private final IdGenerator idGenerator;
    private final MaterialService materialService;
    private final ProductDao productDao;

    @Transactional
    public void addToQueue(String userId, String characterId, AddToQueueRequest request) {
        SkyXpCharacter character = characterService.findCharacterByIdAuthorized(characterId, userId);
        Factory factory = findFactoryOfCharacterValidated(characterId);
        FactoryData elementData = gameDataService.getFactoryData(request.getElementId());

        int price = elementData.getBuildPrice() * request.getAmount();
        validateMoney(character.getMoney(), price);
        validateMaterials(factory.getMaterials(), elementData, request.getAmount());

        Product product = createProduct(factory.getFactoryId(), elementData, request.getAmount());
        character.spendMoney(price);
        removeMaterials(factory.getMaterials(), elementData, request.getAmount());

        log.info(factory.getMaterials().toString());
        characterDao.save(character);
        factoryDao.save(factory);
        productDao.save(product);
    }

    private void validateMoney(int actual, int price){
        if(actual < price){
            throw new NotEnoughMoneyException("Not enough money. Needed: " + price + ", have: " + actual);
        }
    }

    private void validateMaterials(Materials materials, FactoryData elementData, int amount){
        Map<String, Integer> requiredMaterials = elementData.getMaterials();
        Set<String> keys = requiredMaterials.keySet();
        for(String key : keys){
            int required = requiredMaterials.get(key) * amount;
            if(materials.get(key) < required){
                throw new NotEnoughMaterialsException("Not enough " + key +". Needed: " + required + ", have: " + materials.get(key));
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

    public void addMaterial(String factoryId, String elementId, Integer amount){
        Factory factory = factoryDao.findById(factoryId);
        Materials materials = factory.getMaterials();
        materials.addMaterial(elementId, amount);
        factoryDao.save(factory);
    }

    public String getCharacterIdOfFactory(String factoryId){
        return factoryDao.findById(factoryId).getCharacterId();
    }

    public Map<String, MaterialView> getMaterials(String characterId, String userId) {
        characterService.findCharacterByIdAuthorized(characterId, userId);
        Factory factory = findFactoryOfCharacterValidated(characterId);
        Materials materials = factory.getMaterials();
        Map<String, MaterialView> result = fillWithMaterials(materials);
        log.info("Materials successfully queried for character {}", characterId);
        return result;
    }

    private Map<String, MaterialView> fillWithMaterials(Materials materials) {
        Map<String, MaterialView> result = new HashMap<>();
        materialService.keySet().forEach(
            key -> result.put(
                key,
                MaterialView.builder()
                    .materialId(key)
                    .name(materialService.get(key).getName())
                    .description(materialService.get(key).getDescription())
                    .amount(materials.get(key))
                    .build()
            )
        );
        return result;
    }

    private Factory findFactoryOfCharacterValidated(String characterId) {
        Factory factory = factoryDao.findByCharacterId(characterId);
        if (factory == null) {
            throw new FactoryNotFoundException("Factory not found for character " + characterId);
        }
        return factory;
    }

    public String getFactoryIdOfCharacter(String characterId){
        return findFactoryOfCharacterValidated(characterId).getFactoryId();
    }
}
