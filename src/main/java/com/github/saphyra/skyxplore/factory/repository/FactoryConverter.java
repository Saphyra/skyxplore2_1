package com.github.saphyra.skyxplore.factory.repository;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.factory.domain.Factory;
import org.springframework.stereotype.Component;
import com.github.saphyra.converter.ConverterBase;

@Component
@RequiredArgsConstructor
class FactoryConverter extends ConverterBase<FactoryEntity, Factory> {
    private final MaterialsConverter materialsConverter;

    @Override
    public Factory processEntityConversion(FactoryEntity entity) {
        return Factory.builder()
            .characterId(entity.getCharacterId())
            .factoryId(entity.getFactoryId())
            .materials(materialsConverter.convertEntity(entity.getMaterials(), entity.getFactoryId()))
            .build();
    }

    @Override
    public FactoryEntity processDomainConversion(Factory domain) {
        return FactoryEntity.builder()
            .factoryId(domain.getFactoryId())
            .characterId(domain.getCharacterId())
            .materials(materialsConverter.convertDomain(domain.getMaterials(), domain.getFactoryId()))
            .build();
    }
}
