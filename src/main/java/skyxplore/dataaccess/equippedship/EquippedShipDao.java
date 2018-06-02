package skyxplore.dataaccess.equippedship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.equippedship.converter.EquippedShipConverter;
import skyxplore.dataaccess.equippedship.repository.EquippedShipRepository;
import skyxplore.service.domain.EquippedShip;

@Component
@RequiredArgsConstructor
@Slf4j
public class EquippedShipDao {
    private final EquippedShipRepository equippedShipRepository;
    private final EquippedShipConverter equippedShipConverter;

    public EquippedShip save(EquippedShip ship) {
        return equippedShipConverter.convertEntity(
                equippedShipRepository.save(equippedShipConverter.convertDomain(ship))
        );
    }
}
