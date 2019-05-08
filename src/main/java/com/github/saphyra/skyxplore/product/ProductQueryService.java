package com.github.saphyra.skyxplore.product;

import com.github.saphyra.skyxplore.factory.FactoryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.product.domain.Product;
import com.github.saphyra.skyxplore.product.domain.ProductView;
import com.github.saphyra.skyxplore.product.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductQueryService {
    private final FactoryQueryService factoryQueryService;
    private final ProductDao productDao;
    private final ProductViewConverter productViewConverter;

    public List<Product> getFirstFromQueue() {
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

    List<ProductView> getQueue(String characterId) {
        String factoryId = factoryQueryService.getFactoryIdOfCharacter(characterId);

        List<Product> queue = productDao.getByFactoryId(factoryId);

        return productViewConverter.convertDomain(queue);
    }
}
