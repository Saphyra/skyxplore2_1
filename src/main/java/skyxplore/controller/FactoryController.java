package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.controller.view.product.ProductViewList;
import skyxplore.filter.AuthFilter;
import skyxplore.filter.CharacterAuthFilter;
import skyxplore.service.FactoryFacade;
import skyxplore.service.ProductFacade;

import javax.validation.Valid;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor

public class FactoryController {
    private static final String ADD_TO_QUEUE_MAPPING = "factory";
    private static final String GET_MATERIALS_MAPPING = "factory/materials/{characterId}";
    private static final String GET_QUEUE_MAPPING = "factory/queue/{characterId}";

    private final FactoryFacade factoryFacade;
    private final ProductFacade productFacade;

    @PutMapping(ADD_TO_QUEUE_MAPPING)
    public void addToQueue(
        @RequestBody @Valid AddToQueueRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("Character {} wants to add material {}", characterId,  request);
        factoryFacade.addToQueue(characterId, request);
    }

    @GetMapping(GET_MATERIALS_MAPPING)
    public Map<String, MaterialView> getMaterials(@PathVariable("characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know the materials of character {}", userId, characterId);
        return factoryFacade.getMaterials(characterId, userId);
    }

    @GetMapping(GET_QUEUE_MAPPING)
    public View<ProductViewList> getQueue(@PathVariable("characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know queue of character {}", userId, characterId);
        return productFacade.getQueue(userId, characterId);
    }
}
