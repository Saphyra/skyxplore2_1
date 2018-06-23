package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.filter.AuthFilter;
import skyxplore.restcontroller.view.material.MaterialView;
import skyxplore.service.FactoryService;

import java.util.Map;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
public class FactoryController {
    private static final String GET_MATERIALS_MAPPING = "factory/materials/{characterId}";

    private final FactoryService factoryService;

    @GetMapping(GET_MATERIALS_MAPPING)
    public Map<String, MaterialView> getMaterials(@PathVariable(name = "characterId") String characterId, @CookieValue(name = AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know the materials of character {}", userId, characterId);
        return factoryService.getMaterials(characterId, userId);
    }
}
