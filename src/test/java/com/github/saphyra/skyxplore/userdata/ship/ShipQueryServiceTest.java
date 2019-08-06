package com.github.saphyra.skyxplore.userdata.ship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.exception.ShipNotFoundException;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.ShipView;
import com.github.saphyra.skyxplore.userdata.ship.repository.EquippedShipDao;
import com.github.saphyra.skyxplore.userdata.slot.SlotQueryService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;

@RunWith(MockitoJUnitRunner.class)
public class ShipQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";

    @Mock
    private EquippedShipDao equippedShipDao;

    @Mock
    private ShipViewConverter shipViewConverter;

    @Mock
    private SlotQueryService slotQueryService;

    @InjectMocks
    private ShipQueryService underTest;

    @Mock
    private EquippedShip ship;

    @Mock
    private EquippedSlot defenseSlot;

    @Mock
    private EquippedSlot weaponSlot;

    @Mock
    private ShipView shipView;

    @Test(expected = ShipNotFoundException.class)
    public void testGetShipByCharacterIdShouldThrowExceptionWhenNull() {
        //GIVEN
        when(equippedShipDao.findShipByCharacterId(CHARACTER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.findShipbyCharacterIdValidated(CHARACTER_ID);
    }

    @Test
    public void testGetShipByCharacterIdShouldQueryAndReturn() {
        //GIVEN
        when(equippedShipDao.findShipByCharacterId(CHARACTER_ID)).thenReturn(Optional.of(ship));
        //WHEN
        EquippedShip result = underTest.findShipbyCharacterIdValidated(CHARACTER_ID);
        //THEN
        verify(equippedShipDao).findShipByCharacterId(CHARACTER_ID);
        assertThat(result).isEqualTo(ship);
    }

    @Test
    public void testGetShipDataShouldReturn() {
        //GIVEN
        given(ship.getDefenseSlotId()).willReturn(DEFENSE_SLOT_ID);
        given(ship.getWeaponSlotId()).willReturn(WEAPON_SLOT_ID);
        when(equippedShipDao.findShipByCharacterId(CHARACTER_ID)).thenReturn(Optional.of(ship));

        when(slotQueryService.findSlotByIdValidated(DEFENSE_SLOT_ID)).thenReturn(defenseSlot);
        when(slotQueryService.findSlotByIdValidated(WEAPON_SLOT_ID)).thenReturn(weaponSlot);

        when(shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot)).thenReturn(shipView);

        //WHEN
        ShipView result = underTest.getShipData(CHARACTER_ID);
        //THEN
        verify(shipViewConverter).convertDomain(ship, defenseSlot, weaponSlot);
        assertThat(result).isEqualTo(shipView);
    }
}