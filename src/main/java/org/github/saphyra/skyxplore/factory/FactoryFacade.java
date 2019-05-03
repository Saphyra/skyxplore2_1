package org.github.saphyra.skyxplore.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryFacade {
    private final AddToQueueService addToQueueService;
    private final FactoryCreatorService factoryCreatorService;
    private final FactoryQueryService factoryQueryService;

    void addToQueue(String characterId, AddToQueueRequest request) {
        addToQueueService.addToQueue(characterId, request);
    }

    //TODO unit test
    public void createFactory(String characterId){
        factoryCreatorService.createFactory(characterId);
    }

    public Map<String, Integer> getMaterials(String characterId) {
        return factoryQueryService.getMaterials(characterId);
    }
}
