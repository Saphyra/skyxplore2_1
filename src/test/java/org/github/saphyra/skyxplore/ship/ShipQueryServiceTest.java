package org.github.saphyra.skyxplore.ship;

import org.github.saphyra.skyxplore.common.exception.ShipNotFoundException;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.domain.ShipView;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.SlotQueryService;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShipQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";
    private static final String SHIP_TYPE = "ship_type";
    @Mock
    private EquippedShipDao equippedShipDao;

    @Mock
    private ShipViewConverter shipViewConverter;

    @Mock
    private SlotQueryService slotQueryService;

    @InjectMocks
    private ShipQueryService underTest;

    @Test(expected = ShipNotFoundException.class)
    public void testGetShipByCharacterIdShouldThrowExceptionWhenNull() {
        //GIVEN
        when(equippedShipDao.findShipByCharacterId(CHARACTER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.getShipByCharacterId(CHARACTER_ID);
    }

    @Test
    public void testGetShipByCharacterIdShouldQueryAndReturn() {
        //GIVEN
        EquippedShip ship = EquippedShip.builder().build();
        when(equippedShipDao.findShipByCharacterId(CHARACTER_ID)).thenReturn(Optional.of(ship));
        //WHEN
        EquippedShip result = underTest.getShipByCharacterId(CHARACTER_ID);
        //THEN
        verify(equippedShipDao).findShipByCharacterId(CHARACTER_ID);
        assertThat(result).isEqualTo(ship);
    }

    @Test
    public void testGetShipDataShouldReturn() {
        //GIVEN
        EquippedShip ship = EquippedShip.builder()
            .defenseSlotId(DEFENSE_SLOT_ID)
            .weaponSlotId(WEAPON_SLOT_ID)
            .build();
        when(equippedShipDao.findShipByCharacterId(CHARACTER_ID)).thenReturn(Optional.of(ship));

        EquippedSlot defenseSlot = EquippedSlot.builder()
            .slotId(DEFENSE_SLOT_ID)
            .build();
        EquippedSlot weaponSlot = EquippedSlot.builder()
            .slotId(WEAPON_SLOT_ID)
            .build();
        when(slotQueryService.findSlotById(DEFENSE_SLOT_ID)).thenReturn(defenseSlot);
        when(slotQueryService.findSlotById(WEAPON_SLOT_ID)).thenReturn(weaponSlot);

        ShipView shipView = new ShipView();
        shipView.setShipType(SHIP_TYPE);
        when(shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot)).thenReturn(shipView);

        //WHEN
        ShipView result = underTest.getShipData(CHARACTER_ID);
        //THEN
        verify(shipViewConverter).convertDomain(ship, defenseSlot, weaponSlot);
        assertThat(result).isEqualTo(shipView);
    }
}