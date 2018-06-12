package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.ship.EquippedShipConverter;
import skyxplore.domain.ship.EquippedShipEntity;
import skyxplore.dataaccess.db.repository.EquippedShipRepository;
import skyxplore.exception.ShipNotFoundException;
import skyxplore.domain.ship.EquippedShip;

@Component
@RequiredArgsConstructor
@Slf4j
public class EquippedShipDao {
    private final EquippedShipRepository equippedShipRepository;
    private final SlotDao slotDao;

    private final EquippedShipConverter equippedShipConverter;

    public void deleteByCharacterId(String characterId){
        EquippedShipEntity ship = equippedShipRepository.getByCharacterId(characterId);
        if(ship == null){
            throw new ShipNotFoundException("Ship not found for character " + characterId);
        }
        slotDao.deleteByShipId(ship.getShipId());

        log.info("Deleting ship of {}", characterId);
        equippedShipRepository.delete(ship);
    }

    public EquippedShip getShipByCharacterId(String characterId) {
        return equippedShipConverter.convertEntity(
                equippedShipRepository.getByCharacterId(characterId)
        );
    }

    public EquippedShip save(EquippedShip ship) {
        return equippedShipConverter.convertEntity(
                equippedShipRepository.save(equippedShipConverter.convertDomain(ship))
        );
    }
}
