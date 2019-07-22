package com.github.saphyra.skyxplore.gamedata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.github.saphyra.skyxplore.gamedata.subservice.CommonDataQueryService;

import java.util.List;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
class DataController {
    private static final String GET_ITEMS_OF_CATEGORY_MAPPING = API_PREFIX + "/categories/{categoryId}";

    private final CommonDataQueryService commonDataQueryService;

    @GetMapping(GET_ITEMS_OF_CATEGORY_MAPPING)
    List<String> getItemsOfCategory(@PathVariable("categoryId") String categoryId) {
        log.info("Querying items of category {}", categoryId);
        return commonDataQueryService.getItemsOfCategory(categoryId);
    }
}
