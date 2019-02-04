package skyxplore.controller;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.controller.request.character.EquipmentCategoryRequest;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.CategoryService;
import skyxplore.service.GameDataFacade;

import java.util.Map;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
public class GameDataController {
    private static final String EQUIPMENT_CATEGORIES_MAPPING = "data/equipment/categories/{categoryId}";
    private static final String EQUIPMENTS_OF_CATEGORY_MAPPING = "data/equipment/category/{category}";

    private final CategoryService categoryService;
    private final GameDataFacade gameDataFacade;

    @GetMapping(EQUIPMENT_CATEGORIES_MAPPING)
    public String getCategories(@PathVariable(name = "categoryId") String categoryId) {
        log.info("Request arrived to {}", EQUIPMENT_CATEGORIES_MAPPING);
        String content = categoryService.get(categoryId + "_categories");
        if (content == null) {
            throw new NotFoundException("Category list not found with id " + categoryId);
        }
        return content;
    }

    @GetMapping(EQUIPMENTS_OF_CATEGORY_MAPPING)
    public Map<String, GeneralDescription> getElementsOfCategory(@PathVariable String category) {
        log.info("Request arrived to {} with argument {}", EQUIPMENTS_OF_CATEGORY_MAPPING, category);
        return gameDataFacade.getElementsOfCategory(EquipmentCategoryRequest.fromValue(category));
    }
}
