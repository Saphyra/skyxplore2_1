package org.github.saphyra.skyxplore.product;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.github.saphyra.skyxplore.product.repository.ProductDao;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class ProductFactory {
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;
    private final ProductDao productDao;

    void createAndSave(String factoryId, FactoryData factoryData, Integer amount) {
        Product product = Product.builder()
            .productId(idGenerator.generateRandomId())
            .factoryId(factoryId)
            .elementId(factoryData.getId())
            .amount(amount)
            .constructionTime(factoryData.getConstructionTime() * amount)
            .addedAt(dateTimeUtil.convertDomain(dateTimeUtil.now()))
            .build();
        log.debug("Product created: {}", product);
        productDao.save(product);
    }
}