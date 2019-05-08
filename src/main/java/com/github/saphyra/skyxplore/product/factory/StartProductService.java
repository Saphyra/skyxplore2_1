package com.github.saphyra.skyxplore.product.factory;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.product.ProductQueryService;
import com.github.saphyra.skyxplore.product.domain.Product;
import com.github.saphyra.skyxplore.product.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class StartProductService {
    private final DateTimeUtil dateTimeUtil;
    private final ProductDao productDao;
    private final ProductQueryService productQueryService;

    void startProducts() {
        log.info("Starting the next product in the queue");
        List<Product> products = productQueryService.getFirstFromQueue();

        log.info("Items to start: {}", products.size());
        log.debug("Starting items: {}", products);
        products.forEach(this::startNextProduct);
    }

    private void startNextProduct(Product product) {
        try {
            log.info("Start building: {}", product);
            OffsetDateTime startTime = dateTimeUtil.now();
            OffsetDateTime endTime = startTime.plusSeconds(product.getConstructionTime());
            product.setStartTime(startTime);
            product.setEndTime(endTime);
            productDao.save(product);
        } catch (Exception e) {
            log.error("Error occurred during starting new product {}", product, e);
        }
    }
}
