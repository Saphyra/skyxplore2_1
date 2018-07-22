package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.filter.AuthFilter;
import skyxplore.restcontroller.request.EquipRequest;
import skyxplore.restcontroller.request.UnequipRequest;
import skyxplore.restcontroller.view.View;
import skyxplore.restcontroller.view.ship.ShipView;
import skyxplore.service.EquippedShipService;

@SuppressWarnings("unused")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ShipController {
    private static final String EQUIP_MAPPING = "ship/equip/{characterId}";
    private static final String GET_SHIP_DATA_MAPPING = "ship/{characterId}";
    private static final String UNEQUIP_MAPPING = "ship/unequip/{characterId}";

    private final EquippedShipService equippedShipService;

    @PostMapping(EQUIP_MAPPING)
    public void equip(@RequestBody EquipRequest request, @PathVariable(value = "characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to equip {} to character {}", userId, request, characterId);
        equippedShipService.equip(request, userId, characterId);
    }

    @GetMapping(GET_SHIP_DATA_MAPPING)
    public View<ShipView> getShipData(@PathVariable(value = "characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("Querying ship data of character {}", characterId);
        return equippedShipService.getShipData(characterId, userId);
    }

    @PostMapping(UNEQUIP_MAPPING)
    public void unequip(@RequestBody UnequipRequest request, @PathVariable(value = "characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to unequip {} from character {}'s ship", userId, request, characterId);
        equippedShipService.unequip(request, userId, characterId);
    }
}
