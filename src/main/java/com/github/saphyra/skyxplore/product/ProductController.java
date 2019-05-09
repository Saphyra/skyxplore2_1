package com.github.saphyra.skyxplore.product;

import com.github.saphyra.skyxplore.filter.CustomFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.product.domain.ProductView;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
class ProductController {
    private static final String GET_QUEUE_MAPPING = "factory/queue";

    private final ProductQueryService productQueryService;

    @GetMapping(GET_QUEUE_MAPPING)
    List<ProductView> getQueue(@CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know his queue", characterId);
        return productQueryService.getQueue(characterId);
    }
}