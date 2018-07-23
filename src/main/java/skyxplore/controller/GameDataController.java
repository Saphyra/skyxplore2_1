package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.controller.request.EquipmentCategoryRequest;
import skyxplore.service.GameDataFacade;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
public class GameDataController {
    private static final String EQUIPMENT_CATEGORIES_MAPPING = "data/equipment/categories/{categoryId}";
    private static final String EQUIPMENTS_OF_CATEGORY_MAPPING = "data/equipment/category/{category}";

    private final GameDataFacade gameDataFacade;

    @GetMapping(EQUIPMENT_CATEGORIES_MAPPING)
    public String getCategories(@PathVariable(name = "categoryId") String categoryId) {
        log.info("Request arrived to {}", EQUIPMENT_CATEGORIES_MAPPING);
        File file = new File("src/main/resources/data/" + categoryId + "_categories.json");
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            log.info("Category cannot be loaded with id {}", categoryId);
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @GetMapping(EQUIPMENTS_OF_CATEGORY_MAPPING)
    public Map<String, GeneralDescription> getElementsOfCategory(@PathVariable String category){
        log.info("Request arrived to {} with argument {}", EQUIPMENTS_OF_CATEGORY_MAPPING, category);
        return gameDataFacade.getElementsOfCategory(EquipmentCategoryRequest.fromValue(category));
    }
}
