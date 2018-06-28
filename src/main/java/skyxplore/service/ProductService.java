package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.domain.product.Product;
import skyxplore.restcontroller.view.View;
import skyxplore.restcontroller.view.product.ProductViewConverter;
import skyxplore.restcontroller.view.product.ProductViewList;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final CharacterService characterService;
    private final FactoryService factoryService;
    private final GameDataService gameDataService;
    private final ProductDao productDao;
    private final ProductViewConverter productViewConverter;

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
}
