package skyxplore.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.view.product.ProductView;
import skyxplore.service.product.ProductQueryService;

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
