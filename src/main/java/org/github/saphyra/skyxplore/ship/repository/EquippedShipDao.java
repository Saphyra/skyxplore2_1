package org.github.saphyra.skyxplore.ship.repository;

import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.common.exception.ShipNotFoundException;
import org.github.saphyra.skyxplore.event.CharacterDeletedEvent;
import org.github.saphyra.skyxplore.event.ShipDeletedEvent;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class EquippedShipDao extends AbstractDao<EquippedShipEntity, EquippedShip, String, EquippedShipRepository> {
    private final ApplicationEventPublisher eventPublisher;

    public EquippedShipDao(
        EquippedShipConverter converter,
        EquippedShipRepository repository,
        ApplicationEventPublisher eventPublisher
    ) {
        super(converter, repository);
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    void deleteByCharacterId(CharacterDeletedEvent event) {
        EquippedShipEntity ship = repository.findByCharacterId(event.getCharacterId())
            .orElseThrow(() -> new ShipNotFoundException("EquippedShip not found with characterId " + event.getCharacterId()));
        eventPublisher.publishEvent(new ShipDeletedEvent(ship.getShipId()));
        log.info("Deleting ship of {}", event);
        repository.delete(ship);
    }

    public Optional<EquippedShip> findShipByCharacterId(String characterId) {
        return converter.convertEntity(repository.findByCharacterId(characterId));
    }
}
