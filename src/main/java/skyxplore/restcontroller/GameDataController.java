package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.dataaccess.gamedata.GameDataService;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.restcontroller.request.EquipmentCategoryRequest;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GameDataController {
    private static final String EQUIPMENT_CATEGORIES_MAPPING = "data/equipment/category";
    private static final String EQUIPMENTS_OF_CATEGORY_MAPPING = "data/equipment/category/{category}";

    private final GameDataService gameDataService;

    @GetMapping(EQUIPMENT_CATEGORIES_MAPPING)
    public String getCategories() throws IOException {
        log.info("Request arrived to {}", EQUIPMENT_CATEGORIES_MAPPING);
        File file = new File("src/main/resources/data/equipment_categories.json");
        return FileUtils.readFileToString(file);
    }
    
    @GetMapping(EQUIPMENTS_OF_CATEGORY_MAPPING)
    public Map<String, GeneralDescription> getElementsOfCategory(@PathVariable String category){
        log.info("Request arrived to {} with argument {}", EQUIPMENTS_OF_CATEGORY_MAPPING, category);
        return gameDataService.getElementsOfCategory(EquipmentCategoryRequest.fromValue(category));
    }
}
