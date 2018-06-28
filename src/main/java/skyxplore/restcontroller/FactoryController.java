package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.filter.AuthFilter;
import skyxplore.restcontroller.request.AddToQueueRequest;
import skyxplore.restcontroller.view.View;
import skyxplore.restcontroller.view.material.MaterialView;
import skyxplore.restcontroller.view.product.ProductViewList;
import skyxplore.service.FactoryService;
import skyxplore.service.ProductService;

import javax.validation.Valid;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
public class FactoryController {
    private static final String ADD_TO_QUEUE_MAPPING = "factory/{characterId}";
    private static final String GET_MATERIALS_MAPPING = "factory/materials/{characterId}";
    private static final String GET_QUEUE_MAPPING = "factory/queue/{characterId}";

    private final FactoryService factoryService;
    private final ProductService productService;

    @PutMapping(ADD_TO_QUEUE_MAPPING)
    public void addToQueue(@PathVariable("characterId") String characterId, @RequestBody @Valid AddToQueueRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("User {} adds material {} to character {}", userId, request, characterId);
        factoryService.addToQueue(userId, characterId, request);
    }

    @GetMapping(GET_MATERIALS_MAPPING)
    public Map<String, MaterialView> getMaterials(@PathVariable("characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know the materials of character {}", userId, characterId);
        return factoryService.getMaterials(characterId, userId);
    }

    @GetMapping(GET_QUEUE_MAPPING)
    public View<ProductViewList> getQueue(@PathVariable("characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to know queue of character {}", userId, characterId);
        return productService.getQueue(userId, characterId);
    }
}
