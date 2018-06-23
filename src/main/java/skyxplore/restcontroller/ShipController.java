package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.filter.AuthFilter;
import skyxplore.restcontroller.view.EquipmentView;
import skyxplore.restcontroller.view.ship.ShipView;
import skyxplore.service.EquippedShipService;

@SuppressWarnings("unused")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ShipController {
    private static final String GET_SHIP_DATA_MAPPING = "ship/{characterId}";

    private final EquippedShipService equippedShipService;

    @GetMapping(GET_SHIP_DATA_MAPPING)
    public EquipmentView<ShipView> getShipData(@PathVariable(value = "characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("Querying ship data of character {}", characterId);
        return equippedShipService.getShipData(characterId, userId);
    }
}
