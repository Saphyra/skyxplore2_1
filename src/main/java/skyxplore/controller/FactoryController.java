package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.filter.AuthFilter;
import skyxplore.controller.request.AddToQueueRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.controller.view.product.ProductViewList;
import skyxplore.service.FactoryFacade;
import skyxplore.service.ProductFacade;

import javax.validation.Valid;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class FactoryController {
    private static final String ADD_TO_QUEUE_MAPPING = "factory/{characterId}";
    private static final String GET_MATERIALS_MAPPING = "factory/materials/{characterId}";
    private static final String GET_QUEUE_MAPPING = "factory/queue/{characterId}";

    private final FactoryFacade factoryFacade;
    private final ProductFacade productFacade;

    @PutMapping(ADD_TO_QUEUE_MAPPING)
    public void addToQueue(@PathVariable("characterId") String characterId, @RequestBody @Valid AddToQueueRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("User {} adds material {} to character {}", userId, request, characterId);
        factoryFacade.addToQueue(userId, characterId, request);
    }

    @GetMapping(GET_MATERIALS_MAPPING)
    public Map<String, MaterialView> getMaterials(@PathVariable("characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know the materials of character {}", userId, characterId);
        return factoryFacade.getMaterials(characterId, userId);
    }

    @GetMapping(GET_QUEUE_MAPPING)
    public View<ProductViewList> getQueue(@PathVariable("characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to know queue of character {}", userId, characterId);
        return productFacade.getQueue(userId, characterId);
    }
}
