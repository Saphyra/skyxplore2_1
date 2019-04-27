package skyxplore.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.factory.FactoryQueryService;
import skyxplore.util.DateTimeUtil;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
//TODO split class
public class ProductFactoryService {
    private final CharacterQueryService characterQueryService;
    private final CharacterDao characterDao;
    private final DateTimeUtil dateTimeUtil;
    private final FactoryQueryService factoryQueryService;
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
        products.forEach(product -> {
            try {
                finishProduct(product);
            } catch (Exception e) {
                log.error("Error occurred during finishing product {}", product, e);
            }
        });
    }

    @Transactional
    private void finishProduct(Product product) {
        log.info("Finishing product {}", product);
        GeneralDescription elementData = gameDataFacade.getData(product.getElementId());
        if (elementData instanceof Material) {
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

    private void startNextProduct() {
        log.info("Starting the next product in the queue");
        List<Product> products = getFirstProductsFromQueue();

        log.info("Items to start: {}", products.size());
        log.debug("Starting items: {}", products);
        products.forEach(this::startNextProduct);
    }

    //TODO unit test
    private List<Product> getFirstProductsFromQueue() {
        List<Product> products = productDao.getFirstOfQueue();
        List<Product> result = new ArrayList<>();
        List<String> factoryIds = new ArrayList<>();

        products.forEach(product -> {
            if (!factoryIds.contains(product.getFactoryId())) {
                result.add(product);
                factoryIds.add(product.getFactoryId());
            }else{
                log.info("{} is filtered, factory {} is already busy.", product.getProductId(), product.getFactoryId());
            }
        });

        return result;
    }

    private void startNextProduct(Product product) {
        try {
            startBuilding(product);
        } catch (Exception e) {
            log.error("Error occurred during starting new product {}", product, e);
        }
    }

    @Transactional
    private void startBuilding(Product product) {
        log.info("Start building: {}", product);
        OffsetDateTime startTime = dateTimeUtil.now();
        OffsetDateTime endTime = startTime.plusSeconds(product.getConstructionTime());
        product.setStartTime(startTime);
        product.setEndTime(endTime);
        productDao.save(product);
    }
}
