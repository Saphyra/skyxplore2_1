package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.domain.product.Product;
import skyxplore.exception.FactoryNotFoundException;
import skyxplore.exception.NotEnoughMaterialsException;
import skyxplore.exception.NotEnoughMoneyException;
import skyxplore.controller.request.AddToQueueRequest;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.factory.AddToQueueService;
import skyxplore.service.factory.FactoryQueryService;
import skyxplore.util.IdGenerator;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
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
