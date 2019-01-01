package skyxplore.dataaccess.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FactoryRepository;
import com.github.saphyra.converter.Converter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.factory.FactoryEntity;

@SuppressWarnings("WeakerAccess")
@Component
@Slf4j
public class FactoryDao extends AbstractDao<FactoryEntity, Factory, String, FactoryRepository> {
    private final ProductDao productDao;

    public FactoryDao(
        Converter<FactoryEntity, Factory> converter,
        FactoryRepository repository,
        ProductDao productDao
    ) {
        super(converter, repository);
        this.productDao = productDao;
    }

    public void deleteByCharacterId(String characterId) {
        FactoryEntity factoryEntity = repository.findByCharacterId(characterId);
        productDao.deleteByFactoryId(factoryEntity.getFactoryId());
        repository.deleteByCharacterId(characterId);
    }

    public Factory findByCharacterId(String characterId) {
        return converter.convertEntity(repository.findByCharacterId(characterId));
    }
}
