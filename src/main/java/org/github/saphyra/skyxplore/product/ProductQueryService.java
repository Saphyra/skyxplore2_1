package org.github.saphyra.skyxplore.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.github.saphyra.skyxplore.product.domain.ProductView;
import org.github.saphyra.skyxplore.product.repository.ProductDao;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.factory.FactoryQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductQueryService {
    private final FactoryQueryService factoryQueryService;
    private final ProductDao productDao;
    private final ProductViewConverter productViewConverter;

    public List<ProductView> getQueue(String characterId) {
        String factoryId = factoryQueryService.getFactoryIdOfCharacter(characterId);

        List<Product> queue = productDao.findByFactoryId(factoryId);

        return productViewConverter.convertDomain(queue);
    }
}