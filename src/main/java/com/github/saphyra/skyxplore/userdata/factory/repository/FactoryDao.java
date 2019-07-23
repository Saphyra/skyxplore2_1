package com.github.saphyra.skyxplore.userdata.factory.repository;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.exception.FactoryNotFoundException;
import com.github.saphyra.skyxplore.common.event.CharacterDeletedEvent;
import com.github.saphyra.skyxplore.common.event.FactoryDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class FactoryDao extends AbstractDao<FactoryEntity, Factory, String, FactoryRepository> {
    private final ApplicationEventPublisher eventPublisher;

    public FactoryDao(
        Converter<FactoryEntity, Factory> converter,
        FactoryRepository repository,
        ApplicationEventPublisher eventPublisher
    ) {
        super(converter, repository);
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void deleteByCharacterId(CharacterDeletedEvent event) {
        FactoryEntity factoryEntity = repository.findByCharacterId(event.getCharacterId())
            .orElseThrow(() -> new FactoryNotFoundException("Factory not found for character " + event.getCharacterId()));
        eventPublisher.publishEvent(new FactoryDeletedEvent(factoryEntity.getFactoryId()));
        repository.delete(factoryEntity);
    }

    public Optional<Factory> findByCharacterId(String characterId) {
        return converter.convertEntity(repository.findByCharacterId(characterId));
    }
}
