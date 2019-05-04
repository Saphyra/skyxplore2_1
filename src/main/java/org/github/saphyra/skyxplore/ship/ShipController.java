package org.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.ship.domain.ShipView;
import org.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
class ShipController {
    private static final String EQUIP_MAPPING = "ship/equipment";
    private static final String EQUIP_SHIP_MAPPING = "ship/equipment/{shipId}";
    private static final String GET_SHIP_DATA_MAPPING = "ship";
    private static final String UNEQUIP_MAPPING = "ship/equipment";

    private final EquipService equipService;
    private final EquipShipService equipShipService;
    private final ShipQueryService shipQueryService;
    private final UnequipService unequipService;

    @PostMapping(EQUIP_MAPPING)
    void equip(
        @RequestBody @Valid EquipRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to equip {}", request, characterId);
        equipService.equip(request, characterId);
    }

    @PostMapping(EQUIP_SHIP_MAPPING)
    void equipShip(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @PathVariable("shipId") String shipId
    ) {
        log.info("{} wants to equip a new ship {}", shipId, characterId);
        equipShipService.equipShip(characterId, shipId);
    }

    @GetMapping(GET_SHIP_DATA_MAPPING)
    ShipView getShipData(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("Querying ship data of character {}", characterId);
        return shipQueryService.getShipData(characterId);
    }

    @DeleteMapping(UNEQUIP_MAPPING)
    void unequip(
        @RequestBody @Valid UnequipRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to unequip {}", characterId, request);
        unequipService.unequip(request, characterId);
    }
}
