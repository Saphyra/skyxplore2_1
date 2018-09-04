package skyxplore.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.filter.AuthFilter;
import skyxplore.controller.request.character.EquipRequest;
import skyxplore.controller.request.character.UnequipRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.ship.ShipView;
import skyxplore.service.EquippedShipFacade;

@SuppressWarnings("unused")
@RestController
@RequiredArgsConstructor
@Slf4j
//TODO unit test
//TODO eliminate characterIds in path
public class ShipController {
    private static final String EQUIP_MAPPING = "ship/equip/{characterId}";
    private static final String EQUIP_SHIP_MAPPING = "ship/equipship/{characterId}/shipid/{shipId}";
    private static final String GET_SHIP_DATA_MAPPING = "ship/{characterId}";
    private static final String UNEQUIP_MAPPING = "ship/unequip/{characterId}";

    private final EquippedShipFacade equippedShipFacade;

    @PostMapping(EQUIP_MAPPING)
    public void equip(@RequestBody @Valid EquipRequest request, @PathVariable(value = "characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to equip {} to character {}", userId, request, characterId);
        equippedShipFacade.equip(request, userId, characterId);
    }

    @PostMapping(EQUIP_SHIP_MAPPING)
    public void equipShip(@PathVariable(value = "characterId") String characterId, @PathVariable("shipId") String shipId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to equip a new ship {} to character {}", userId, shipId, characterId);
        equippedShipFacade.equipShip(userId, characterId, shipId);
    }

    @GetMapping(GET_SHIP_DATA_MAPPING)
    public View<ShipView> getShipData(@PathVariable(value = "characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("Querying ship data of character {}", characterId);
        return equippedShipFacade.getShipData(characterId, userId);
    }

    @PostMapping(UNEQUIP_MAPPING)
    public void unequip(@RequestBody @Valid UnequipRequest request, @PathVariable(value = "characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to unequip {} from character {}'s ship", userId, request, characterId);
        equippedShipFacade.unequip(request, userId, characterId);
    }
}
