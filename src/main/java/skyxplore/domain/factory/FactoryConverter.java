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
        domain.setMaterials(materialsConverter.convertEntity(entity.getMaterials()));

        return domain;
    }

    @Override
    public FactoryEntity convertDomain(Factory domain) {
        if(domain == null){
            return null;
        }
        FactoryEntity entity = new FactoryEntity();
        entity.setFactoryId(domain.getFactoryId());
        entity.setCharacterId(domain.getCharacterId());
        entity.setMaterials(materialsConverter.convertDomain(domain.getMaterials()));

        return entity;
    }
}
