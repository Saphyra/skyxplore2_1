package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.domain.materials.Materials;
import skyxplore.filter.AuthFilter;
import skyxplore.service.FactoryService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FactoryController {
    private static final String GET_MATERIALS_MAPPING = "factory/materials/{characterId}";

    private final FactoryService factoryService;

    @GetMapping(GET_MATERIALS_MAPPING)
    public Materials getMaterials(@PathVariable(name = "characterId") String characterId, @CookieValue(name = AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know the materials of character {}", userId, characterId);
        return factoryService.getMaterials(characterId, userId);
    }
}
