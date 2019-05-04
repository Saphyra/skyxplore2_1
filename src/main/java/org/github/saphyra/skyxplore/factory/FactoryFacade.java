package org.github.saphyra.skyxplore.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class FactoryFacade {
    private final FactoryCreatorService factoryCreatorService;

    public void createFactory(String characterId){
        factoryCreatorService.createFactory(characterId);
    }
}
