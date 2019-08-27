package com.github.saphyra.skyxplore.data.gamedata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.github.saphyra.skyxplore.data.gamedata.subservice.CommonGameDataQueryService;

import java.util.List;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
class GameDataController {
    private static final String GET_ITEMS_OF_CATEGORY_MAPPING = API_PREFIX + "/categories/{categoryId}";

    private final CommonGameDataQueryService commonGameDataQueryService;

    @GetMapping(GET_ITEMS_OF_CATEGORY_MAPPING)
    List<String> getItemsOfCategory(@PathVariable("categoryId") String categoryId) {
        log.info("Querying items of category {}", categoryId);
        return commonGameDataQueryService.getItemsOfCategory(categoryId);
    }
}
