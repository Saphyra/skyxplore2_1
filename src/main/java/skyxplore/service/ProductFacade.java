package skyxplore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.view.View;
import skyxplore.controller.view.product.ProductViewConverter;
import skyxplore.controller.view.product.ProductViewList;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.domain.product.Product;
import skyxplore.service.character.CharacterQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO explode
public class ProductFacade {
    private final CharacterQueryService characterQueryService;
    private final FactoryFacade factoryFacade;
    private final GameDataFacade gameDataFacade;
    private final ProductDao productDao;
    private final ProductViewConverter productViewConverter;

    public View<ProductViewList> getQueue(String userId, String characterId) {
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
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
