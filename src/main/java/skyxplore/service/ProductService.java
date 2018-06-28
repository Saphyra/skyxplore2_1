package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.dataaccess.gamedata.entity.Material;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.domain.product.Product;
import skyxplore.restcontroller.view.View;
import skyxplore.restcontroller.view.product.ProductViewConverter;
import skyxplore.restcontroller.view.product.ProductViewList;
import skyxplore.util.DateTimeConverter;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final CharacterService characterService;
    private final DateTimeConverter dateTimeConverter;
    private final FactoryService factoryService;
    private final GameDataService gameDataService;
    private final MaterialService materialService;
    private final ProductDao productDao;
    private final ProductViewConverter productViewConverter;

    @Transactional
    public void finishProduct(Product product) {
        log.info("Finishing product {}", product);
        GeneralDescription elementData = gameDataService.getData(product.getElementId());
        if (elementData instanceof Material) {
            factoryService.addMaterial(product.getFactoryId(), product.getElementId(), product.getAmount());
        } else {
            characterService.addEquipment(
                factoryService.getCharacterIdOfFactory(product.getFactoryId()),
                product.getElementId(),
                product.getAmount()
            );
        }

        productDao.delete(product);
    }

    public List<Product> getFinishedProducts() {
        return productDao.getFinishedProducts();
    }

    public List<Product> getFirstOfQueue() {
        return productDao.getFirstOfQueue();
    }

    public View<ProductViewList> getQueue(String userId, String characterId) {
        characterService.findCharacterByIdAuthorized(characterId, userId);
        String factoryId = factoryService.getFactoryIdOfCharacter(characterId);

        List<Product> queue = productDao.findByFactoryId(factoryId);
        List<String> elementIds = queue.stream().map(Product::getElementId).collect(Collectors.toList());

        ProductViewList productViews = new ProductViewList();
        productViews.addAll(productViewConverter.convertDomain(queue));

        View<ProductViewList> result = new View<>();
        result.setInfo(productViews);
        result.setData(gameDataService.collectEquipmentData(elementIds));
        return result;
    }

    @Transactional
    public void startBuilding(Product product) {
        log.info("Start building: {}", product);
        LocalDateTime startTime = dateTimeConverter.now();
        LocalDateTime endTime = startTime.plusSeconds(product.getConstructionTime());
        product.setStartTime(startTime);
        product.setEndTime(endTime);
        productDao.save(product);
    }
}
