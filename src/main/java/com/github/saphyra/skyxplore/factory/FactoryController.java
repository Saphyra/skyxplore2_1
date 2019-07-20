package com.github.saphyra.skyxplore.factory;

import com.github.saphyra.skyxplore.common.RequestConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
class FactoryController {
    private static final String ADD_TO_QUEUE_MAPPING = API_PREFIX + "/factory";
    private static final String GET_MATERIALS_MAPPING = API_PREFIX + "/factory/materials";

    private final AddToQueueService addToQueueService;
    private final FactoryQueryService factoryQueryService;

    @PutMapping(ADD_TO_QUEUE_MAPPING)
    void addToQueue(
        @RequestBody @Valid AddToQueueRequest request,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("Character {} wants to add material {}", characterId, request);
        addToQueueService.addToQueue(characterId, request);
    }

    @GetMapping(GET_MATERIALS_MAPPING)
    Map<String, Integer> getMaterials(@CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know his materials", characterId);
        return factoryQueryService.getMaterials(characterId);
    }
}
