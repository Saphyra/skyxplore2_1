package skyxplore.domain.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;
import skyxplore.domain.materials.MaterialsConverter;

@Component
@RequiredArgsConstructor
public class FactoryConverter extends ConverterBase<FactoryEntity, Factory> {
    private final MaterialsConverter materialsConverter;

    @Override
    public Factory convertEntity(FactoryEntity entity) {
        if(entity == null){
            return null;
        }
        Factory domain = new Factory();
        domain.setFactoryId(entity.getFactoryId());
        domain.setCharacterId(entity.getCharacterId());
        domain.setMaterials(materialsConverter.convertEntity(entity.getMaterials(), entity.getFactoryId()));

        return domain;
    }

    @Override
    public FactoryEntity convertDomain(Factory domain) {
        if(domain == null){
            throw new IllegalArgumentException("domain must not be null.");
        }
        FactoryEntity entity = new FactoryEntity();
        entity.setFactoryId(domain.getFactoryId());
        entity.setCharacterId(domain.getCharacterId());
        entity.setMaterials(materialsConverter.convertDomain(domain.getMaterials(), domain.getFactoryId()));

        return entity;
    }
}
