package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FactoryRepository;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.factory.FactoryConverter;
import skyxplore.domain.factory.FactoryEntity;
import skyxplore.exception.FactoryNotFoundException;

import java.util.Optional;

@SuppressWarnings("WeakerAccess")
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

    public Factory findById(String factoryId){
        Optional<FactoryEntity> factory = factoryRepository.findById(factoryId);
        if(factory.isPresent())
        {
            return factoryConverter.convertEntity(factory.get());
        }
        throw new FactoryNotFoundException("Factory not found with id " + factoryId);
    }

    public void save(Factory factory){
        factoryRepository.save(factoryConverter.convertDomain(factory));
    }
}
