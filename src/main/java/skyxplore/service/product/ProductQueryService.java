package skyxplore.service.product;

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
import skyxplore.service.GameDataFacade;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.factory.FactoryQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ProductQueryService {
    private final CharacterQueryService characterQueryService;
    private final FactoryQueryService factoryQueryService;
    private final GameDataFacade gameDataFacade;
    private final ProductDao productDao;
    private final ProductViewConverter productViewConverter;

    public View<ProductViewList> getQueue(String userId, String characterId) {
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        String factoryId = factoryQueryService.getFactoryIdOfCharacter(characterId);

        List<Product> queue = productDao.findByFactoryId(factoryId);
        List<String> elementIds = queue.stream().map(Product::getElementId).collect(Collectors.toList());

        return new View<>(
            new ProductViewList(productViewConverter.convertDomain(queue)),
            gameDataFacade.collectEquipmentData(elementIds)
        );
    }
}
