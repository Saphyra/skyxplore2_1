package skyxplore.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.service.factory.AddToQueueService;
import skyxplore.service.factory.FactoryQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class FactoryFacade {
    private final AddToQueueService addToQueueService;
    private final FactoryQueryService factoryQueryService;

    public void addToQueue(String userId, String characterId, AddToQueueRequest request) {
        addToQueueService.addToQueue(userId, characterId, request);
    }

    public Map<String, MaterialView> getMaterials(String characterId, String userId) {
        return factoryQueryService.getMaterials(characterId, userId);
    }
}
