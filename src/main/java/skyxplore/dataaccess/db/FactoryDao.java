package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FactoryRepository;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.factory.FactoryConverter;
import skyxplore.domain.factory.FactoryEntity;

@Component
@RequiredArgsConstructor
@Slf4j
public class FactoryDao {
    private final FactoryConverter factoryConverter;
    private final FactoryRepository factoryRepository;
    private final ProductDao productDao;

    public void deleteByCharacterId(String characterId){
        FactoryEntity factoryEntity = factoryRepository.findByCharacterId(characterId);
        productDao.deleteByFactoryId(factoryEntity.getFactoryId());
        factoryRepository.deleteByCharacterId(characterId);
    }

    public Factory findByCharacterId(String characterId){
        return factoryConverter.convertEntity(factoryRepository.findByCharacterId(characterId));
    }

    public void save(Factory factory){
        factoryRepository.save(factoryConverter.convertDomain(factory));
    }
}
