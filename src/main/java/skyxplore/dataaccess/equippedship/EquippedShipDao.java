package skyxplore.dataaccess.equippedship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.equippedship.repository.EquippedShipRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class EquippedShipDao {
    private final EquippedShipRepository equippedShipRepository;
}
