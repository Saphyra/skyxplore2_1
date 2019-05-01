package org.github.saphyra.skyxplore.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.product.domain.ProductView;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.service.ProductFacade;

import java.util.List;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
class ProductController {
    private static final String GET_QUEUE_MAPPING = "factory/queue";

    private final ProductFacade productFacade;

    @GetMapping(GET_QUEUE_MAPPING)
    List<ProductView> getQueue(@CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know his queue", characterId);
        return productFacade.getQueue(characterId);
    }
}
