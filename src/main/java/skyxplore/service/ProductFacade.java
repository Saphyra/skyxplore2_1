package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.dataaccess.gamedata.entity.Material;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.domain.product.Product;
import skyxplore.controller.view.View;
import skyxplore.controller.view.product.ProductViewConverter;
import skyxplore.controller.view.product.ProductViewList;
import skyxplore.util.DateTimeConverter;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductFacade {
    private final CharacterFacade characterFacade;
    private final DateTimeConverter dateTimeConverter;
    private final FactoryFacade factoryFacade;
    private final GameDataFacade gameDataFacade;
    private final MaterialService materialService;
    private final ProductDao productDao;
    private final ProductViewConverter productViewConverter;

    public View<ProductViewList> getQueue(String userId, String characterId) {
        characterFacade.findCharacterByIdAuthorized(characterId, userId);
        String factoryId = factoryFacade.getFactoryIdOfCharacter(characterId);

        List<Product> queue = productDao.findByFactoryId(factoryId);
        List<String> elementIds = queue.stream().map(Product::getElementId).collect(Collectors.toList());

        ProductViewList productViews = new ProductViewList();
        productViews.addAll(productViewConverter.convertDomain(queue));

        View<ProductViewList> result = new View<>();
        result.setInfo(productViews);
        result.setData(gameDataFacade.collectEquipmentData(elementIds));
        return result;
    }
}
