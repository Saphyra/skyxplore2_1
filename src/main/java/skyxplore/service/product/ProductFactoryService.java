package skyxplore.service.product;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.dataaccess.gamedata.entity.Material;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.domain.product.Product;
import skyxplore.service.GameDataFacade;
import skyxplore.util.DateTimeConverter;

@SuppressWarnings("unused")
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class ProductFactoryService {
    private final CharacterDao characterDao;
    private final DateTimeConverter dateTimeConverter;
    private final GameDataFacade gameDataFacade;
    private final FactoryDao factoryDao;
    private final ProductDao productDao;

    @Scheduled(fixedDelay = 10500L)
    public void process() {
        processFinishedProducts();
        startNextProduct();
    }

    private void processFinishedProducts() {
        log.info("Processing finished products...");
        List<Product> products = productDao.getFinishedProducts();
        log.info("Number of finished products: {}", products.size());
        products.forEach(this::finishProduct);
    }

    @Transactional
    private void finishProduct(Product product) {
        log.info("Finishing product {}", product);
        GeneralDescription elementData = gameDataFacade.getData(product.getElementId());
        if (elementData instanceof Material) {
            addMaterialToFactory(product.getFactoryId(), product.getElementId(), product.getAmount());
        } else {
            Factory factory = factoryDao.findById(product.getFactoryId());
            addEquipmentToCharacter(factory.getCharacterId(), product);
        }
        productDao.delete(product);
    }

    private void addMaterialToFactory(String factoryId, String elementId, Integer amount) {
        Factory factory = factoryDao.findById(factoryId);
        Materials materials = factory.getMaterials();
        materials.addMaterial(elementId, amount);
        factoryDao.save(factory);
    }

    private void addEquipmentToCharacter(String characterId, Product product) {
        SkyXpCharacter character = characterDao.findById(characterId);
        character.addEquipments(product.getElementId(), product.getAmount());
        characterDao.save(character);
    }

    private void startNextProduct() {
        log.info("Starting the next product in the queue");
        List<Product> products = productDao.getFirstOfQueue();
        log.info("Items to start: {}", products.size());
        products.forEach(this::startNextProduct);
    }

    private void startNextProduct(Product product) {
        try {
            startBuilding(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    private void startBuilding(Product product) {
        log.info("Start building: {}", product);
        LocalDateTime startTime = dateTimeConverter.now();
        LocalDateTime endTime = startTime.plusSeconds(product.getConstructionTime());
        product.setStartTime(startTime);
        product.setEndTime(endTime);
        productDao.save(product);
    }
}
