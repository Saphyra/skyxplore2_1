package skyxplore.controller;

import static skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.controller.view.product.ProductView;
import skyxplore.service.FactoryFacade;
import skyxplore.service.ProductFacade;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor

public class FactoryController {
    private static final String ADD_TO_QUEUE_MAPPING = "factory";
    private static final String GET_MATERIALS_MAPPING = "factory/materials";
    private static final String GET_QUEUE_MAPPING = "factory/queue";

    private final FactoryFacade factoryFacade;
    private final ProductFacade productFacade;

    @PutMapping(ADD_TO_QUEUE_MAPPING)
    public void addToQueue(
        @RequestBody @Valid AddToQueueRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("Character {} wants to add material {}", characterId, request);
        factoryFacade.addToQueue(characterId, request);
    }

    @GetMapping(GET_MATERIALS_MAPPING)
    public Map<String, Integer> getMaterials(@CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know his materials", characterId);
        return factoryFacade.getMaterials(characterId);
    }

    @GetMapping(GET_QUEUE_MAPPING)
    public List<ProductView> getQueue(@CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know his queue", characterId);
        return productFacade.getQueue(characterId);
    }
}
