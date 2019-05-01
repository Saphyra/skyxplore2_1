package org.github.saphyra.skyxplore.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

@RestController
@Slf4j
@RequiredArgsConstructor
class FactoryController {
    private static final String ADD_TO_QUEUE_MAPPING = "factory";
    private static final String GET_MATERIALS_MAPPING = "factory/materials";

    private final FactoryFacade factoryFacade;

    @PutMapping(ADD_TO_QUEUE_MAPPING)
    void addToQueue(
        @RequestBody @Valid AddToQueueRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("Character {} wants to add material {}", characterId, request);
        factoryFacade.addToQueue(characterId, request);
    }

    @GetMapping(GET_MATERIALS_MAPPING)
    Map<String, Integer> getMaterials(@CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know his materials", characterId);
        return factoryFacade.getMaterials(characterId);
    }
}
