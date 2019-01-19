package skyxplore.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.view.View;
import skyxplore.controller.view.product.ProductViewList;
import skyxplore.service.product.ProductQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductFacade {
    private final ProductQueryService productQueryService;

    public View<ProductViewList> getQueue(String characterId) {
        return productQueryService.getQueue(characterId);
    }
}
