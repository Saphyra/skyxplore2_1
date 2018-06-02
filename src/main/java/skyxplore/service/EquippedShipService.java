package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.equippedship.EquippedShipDao;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquippedShipService {
    private  final EquippedShipDao equippedShipDao;
}
