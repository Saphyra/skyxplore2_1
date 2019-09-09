package com.github.saphyra.skyxplore.userdata.product.factory;

import com.github.saphyra.skyxplore.userdata.factory.FactoryQueryService;
import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import com.github.saphyra.skyxplore.userdata.factory.repository.FactoryDao;
import com.github.saphyra.skyxplore.userdata.product.domain.Product;
import com.github.saphyra.skyxplore.userdata.product.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.data.gamedata.GameDataFacade;
import com.github.saphyra.skyxplore.data.gamedata.entity.GeneralDescription;

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
        log.debug("Processing finished products...");
        List<Product> products = productDao.getFinishedProducts();
        log.debug("Number of finished products: {}", products.size());
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
        SkyXpCharacter character = characterQueryService.findByCharacterIdValidated(characterId);
        character.addEquipments(product.getElementId(), product.getAmount());
        characterDao.save(character);
    }
}
