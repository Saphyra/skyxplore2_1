package org.github.saphyra.skyxplore.factory;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.product.repository.ProductDao;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.FactoryData;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.github.saphyra.skyxplore.common.exception.NotEnoughMaterialsException;
import org.github.saphyra.skyxplore.common.exception.NotEnoughMoneyException;
import org.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO split
class AddToQueueService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final DateTimeUtil dateTimeUtil;
    private final FactoryDao factoryDao;
    private final FactoryQueryService factoryQueryService;
    private final GameDataFacade gameDataFacade;
    private final IdGenerator idGenerator;
    private final ProductDao productDao;

    @Transactional
    void addToQueue(String characterId, AddToQueueRequest request) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        Factory factory = factoryQueryService.findFactoryOfCharacterValidated(characterId);
        FactoryData elementData = gameDataFacade.getFactoryData(request.getElementId());

        if(!elementData.isBuildable()){
            throw new BadRequestException("Element with id " + request.getElementId() + " is not buildable.");
        }

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
            .productId(idGenerator.generateRandomId())
            .factoryId(factoryId)
            .elementId(elementData.getId())
            .amount(amount)
            .constructionTime(elementData.getConstructionTime() * amount)
            .addedAt(dateTimeUtil.convertDomain(dateTimeUtil.now()))
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
