package org.github.saphyra.skyxplore.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.product.domain.ProductView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductFacade {
    private final ProductQueryService productQueryService;

    public List<ProductView> getQueue(String characterId) {
        return productQueryService.getQueue(characterId);
    }
}
