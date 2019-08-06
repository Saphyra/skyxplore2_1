package com.github.saphyra.skyxplore.userdata.product;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.userdata.product.domain.ProductView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
class ProductController {
    private static final String GET_QUEUE_MAPPING = API_PREFIX + "/factory/queue";

    private final ProductQueryService productQueryService;

    @GetMapping(GET_QUEUE_MAPPING)
    List<ProductView> getQueue(@CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know his queue", characterId);
        return productQueryService.getQueue(characterId);
    }
}
