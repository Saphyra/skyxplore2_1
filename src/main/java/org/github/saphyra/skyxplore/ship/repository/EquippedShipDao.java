package org.github.saphyra.skyxplore.ship.repository;

import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.github.saphyra.skyxplore.common.exception.ShipNotFoundException;

@Component
@Slf4j
public class EquippedShipDao extends AbstractDao<EquippedShipEntity, EquippedShip, String, EquippedShipRepository> {
    private final SlotDao slotDao;

    public EquippedShipDao(
        EquippedShipConverter converter,
        EquippedShipRepository repository,
        SlotDao slotDao
    ) {
        super(converter, repository);
        this.slotDao = slotDao;
    }

    public void deleteByCharacterId(String characterId) {
        EquippedShipEntity ship = repository.getByCharacterId(characterId);
        if (ship == null) {
            throw new ShipNotFoundException("Ship not found for character " + characterId);
        }
        slotDao.deleteByShipId(ship.getShipId());

        log.info("Deleting ship of {}", characterId);
        repository.delete(ship);
    }

    public EquippedShip getShipByCharacterId(String characterId) {
        return converter.convertEntity(
            repository.getByCharacterId(characterId)
        );
    }
}
