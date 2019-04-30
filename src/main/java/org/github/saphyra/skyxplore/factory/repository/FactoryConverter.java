package org.github.saphyra.skyxplore.factory.repository;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.springframework.stereotype.Component;
import com.github.saphyra.converter.ConverterBase;

@Component
@RequiredArgsConstructor
class FactoryConverter extends ConverterBase<FactoryEntity, Factory> {
    private final MaterialsConverter materialsConverter;

    @Override
    public Factory processEntityConversion(FactoryEntity entity) {
        if (entity == null) {
            return null;
        }
        Factory domain = new Factory();
        domain.setFactoryId(entity.getFactoryId());
        domain.setCharacterId(entity.getCharacterId());
        domain.setMaterials(materialsConverter.convertEntity(entity.getMaterials(), entity.getFactoryId()));

        return domain;
    }

    @Override
    public FactoryEntity processDomainConversion(Factory domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        FactoryEntity entity = new FactoryEntity();
        entity.setFactoryId(domain.getFactoryId());
        entity.setCharacterId(domain.getCharacterId());
        entity.setMaterials(materialsConverter.convertDomain(domain.getMaterials(), domain.getFactoryId()));

        return entity;
    }
}
