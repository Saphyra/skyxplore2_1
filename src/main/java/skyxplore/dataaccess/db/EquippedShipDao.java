package skyxplore.dataaccess.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.EquippedShipRepository;
import skyxplore.domain.Converter;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.ship.EquippedShipEntity;
import skyxplore.exception.ShipNotFoundException;

@SuppressWarnings("WeakerAccess")
@Component
@Slf4j
public class EquippedShipDao extends AbstractDao<EquippedShipEntity, EquippedShip, String, EquippedShipRepository>{
    private final SlotDao slotDao;

    public EquippedShipDao(
        Converter<EquippedShipEntity, EquippedShip> converter,
        EquippedShipRepository repository,
        SlotDao slotDao
    ) {
        super(converter, repository);
        this.slotDao = slotDao;
    }

    public void deleteByCharacterId(String characterId){
        EquippedShipEntity ship = repository.getByCharacterId(characterId);
        if(ship == null){
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
