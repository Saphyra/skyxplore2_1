package skyxplore.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@Slf4j
public class GameDataController {
    private static final String EQUIPMENT_CATEGORIES_MAPPING = "data/equipment/category";

    @GetMapping(EQUIPMENT_CATEGORIES_MAPPING)
    public String getCategories() throws IOException {
        log.info("Request arrived to {}", EQUIPMENT_CATEGORIES_MAPPING);
        File file = new File("src/main/resources/data/equipment_categories.json");
        return FileUtils.readFileToString(file);
    }
}
