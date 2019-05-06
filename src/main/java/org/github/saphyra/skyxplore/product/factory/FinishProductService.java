package org.github.saphyra.skyxplore.product.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.factory.FactoryQueryService;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.github.saphyra.skyxplore.product.repository.ProductDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class FinishProductService {
    static final String CATEGORY_MATERIAL = "material";

    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final FactoryDao factoryDao;
    private final FactoryQueryService factoryQueryService;
    private final GameDataFacade gameDataFacade;
    private final ProductDao productDao;

    void finishProducts() {
        log.info("Processing finished products...");
        List<Product> products = productDao.getFinishedProducts();
        log.info("Number of finished products: {}", products.size());
        products.forEach(product -> {
            try {
                finishProduct(product);
            } catch (Throwable e) {
                log.error("Error occurred during finishing product {}", product, e);
            }
        });
    }

    private void finishProduct(Product product) {
        log.info("Finishing product {}", product);
        GeneralDescription elementData = gameDataFacade.getData(product.getElementId());
        if (CATEGORY_MATERIAL.equals(elementData.getCategory())) {
            addMaterialToFactory(product.getFactoryId(), product.getElementId(), product.getAmount());
        } else {
            Factory factory = factoryQueryService.findByFactoryId(product.getFactoryId());
            addEquipmentToCharacter(factory.getCharacterId(), product);
        }
        productDao.delete(product);
    }

    private void addMaterialToFactory(String factoryId, String elementId, Integer amount) {
        Factory factory = factoryQueryService.findByFactoryId(factoryId);
        Materials materials = factory.getMaterials();
        materials.addMaterial(elementId, amount);
        factoryDao.save(factory);
    }

    private void addEquipmentToCharacter(String characterId, Product product) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        character.addEquipments(product.getElementId(), product.getAmount());
        characterDao.save(character);
    }
}
