package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.dataaccess.gamedata.subservice.GeneralDescriptionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DataController {
    private static final String GET_ITEMS_OF_CATEGORY_MAPPING = "categories/{categoryId}";

    private final GeneralDescriptionService generalDescriptionService;

    @GetMapping(GET_ITEMS_OF_CATEGORY_MAPPING)
    public List<String> getItemsOfCategory(@PathVariable("categoryId") String categoryId){
        log.info("Querying items of category {}", categoryId);
        return generalDescriptionService.getItemsOfCategory(categoryId);
    }
}
