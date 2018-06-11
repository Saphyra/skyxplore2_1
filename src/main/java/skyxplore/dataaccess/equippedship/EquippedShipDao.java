package skyxplore.dataaccess.equippedship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.equippedship.converter.EquippedShipConverter;
import skyxplore.dataaccess.equippedship.entity.EquippedShipEntity;
import skyxplore.dataaccess.equippedship.repository.EquippedShipRepository;
import skyxplore.service.domain.EquippedShip;

@Component
@RequiredArgsConstructor
@Slf4j
public class EquippedShipDao {
    private final EquippedShipRepository equippedShipRepository;

    private final EquippedShipConverter equippedShipConverter;

    public void deleteByCharacterId(Long characterId){
        EquippedShipEntity ship = equippedShipRepository.findByCharacterId(characterId);
        equippedShipRepository.delete(ship);
    }

    public EquippedShip getShipByCharacterId(Long characterId) {
        return equippedShipConverter.convertEntity(
                equippedShipRepository.findByCharacterId(characterId)
        );
    }

    public EquippedShip save(EquippedShip ship) {
        return equippedShipConverter.convertEntity(
                equippedShipRepository.save(equippedShipConverter.convertDomain(ship))
        );
    }
}
