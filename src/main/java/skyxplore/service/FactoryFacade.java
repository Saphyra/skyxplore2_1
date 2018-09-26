package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.service.factory.AddToQueueService;
import skyxplore.service.factory.FactoryQueryService;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class FactoryFacade {
    private final AddToQueueService addToQueueService;
    private final FactoryQueryService factoryQueryService;

    public void addToQueue(String characterId, AddToQueueRequest request) {
        addToQueueService.addToQueue(characterId, request);
    }

    public Map<String, MaterialView> getMaterials(String characterId) {
        return factoryQueryService.getMaterials(characterId);
    }
}
