package skyxplore.controller;

import static skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.character.EquipRequest;
import skyxplore.controller.request.character.UnequipRequest;
import skyxplore.controller.view.ship.ShipView;
import skyxplore.service.EquippedShipFacade;

@SuppressWarnings("WeakerAccess")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ShipController {
    private static final String EQUIP_MAPPING = "ship/equipment";
    private static final String EQUIP_SHIP_MAPPING = "ship/equipment/{shipId}";
    private static final String GET_SHIP_DATA_MAPPING = "ship";
    private static final String UNEQUIP_MAPPING = "ship/equipment";

    private final EquippedShipFacade equippedShipFacade;

    @PostMapping(EQUIP_MAPPING)
    public void equip(
        @RequestBody @Valid EquipRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to equip {}", request, characterId);
        equippedShipFacade.equip(request, characterId);
    }

    @PostMapping(EQUIP_SHIP_MAPPING)
    public void equipShip(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @PathVariable("shipId") String shipId
    ) {
        log.info("{} wants to equip a new ship {}", shipId, characterId);
        equippedShipFacade.equipShip(characterId, shipId);
    }

    @GetMapping(GET_SHIP_DATA_MAPPING)
    public ShipView getShipData(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("Querying ship data of character {}", characterId);
        return equippedShipFacade.getShipData(characterId);
    }

    @DeleteMapping(UNEQUIP_MAPPING)
    public void unequip(
        @RequestBody @Valid UnequipRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to unequip {}", characterId, request);
        equippedShipFacade.unequip(request, characterId);
    }
}
