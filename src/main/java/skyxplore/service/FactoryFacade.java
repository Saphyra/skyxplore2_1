package skyxplore.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;
import skyxplore.service.factory.AddToQueueService;
import skyxplore.service.factory.FactoryQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryFacade {
    private final AddToQueueService addToQueueService;
    private final FactoryQueryService factoryQueryService;

    public void addToQueue(String characterId, AddToQueueRequest request) {
        addToQueueService.addToQueue(characterId, request);
    }

    public Map<String, Integer> getMaterials(String characterId) {
        return factoryQueryService.getMaterials(characterId);
    }
}
