package com.github.saphyra.skyxplore.userdata.slot;

import com.github.saphyra.skyxplore.common.ShipConstants;
import com.github.saphyra.skyxplore.data.gamedata.entity.Ship;
import com.github.saphyra.skyxplore.data.gamedata.entity.Slot;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
import com.github.saphyra.util.IdGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeaponSlotCreatorServiceTest {
    private static final Integer FRONT = 3;
    private static final Integer SIDE = 1;
    private static final Integer BACK = 2;
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";
    private static final String SHIP_ID = "ship_id";

    @Mock
    private ShipService shipService;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private SlotDao slotDao;

    @Mock
    private Ship ship;

    @InjectMocks
    private WeaponSlotCreatorService underTest;

    @Test
    public void createSlot() {
        //GIVEN
        given(shipService.get(ShipConstants.STARTER_SHIP_ID)).willReturn(ship);
        Slot slot = new Slot();
        slot.setFront(FRONT);
        slot.setSide(SIDE);
        slot.setBack(BACK);
        given(ship.getWeapon()).willReturn(slot);
        given(idGenerator.generateRandomId()).willReturn(WEAPON_SLOT_ID);
        //WHEN
        String result = underTest.createSlot(SHIP_ID);
        //THEN
        assertThat(result).isEqualTo(WEAPON_SLOT_ID);
        ArgumentCaptor<EquippedSlot> argumentCaptor = ArgumentCaptor.forClass(EquippedSlot.class);
        verify(slotDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getSlotId()).isEqualTo(WEAPON_SLOT_ID);
        assertThat(argumentCaptor.getValue().getShipId()).isEqualTo(SHIP_ID);
        assertThat(argumentCaptor.getValue().getFrontSlot()).isEqualTo(FRONT);
        assertThat(argumentCaptor.getValue().getLeftSlot()).isEqualTo(SIDE);
        assertThat(argumentCaptor.getValue().getRightSlot()).isEqualTo(SIDE);
        assertThat(argumentCaptor.getValue().getBackSlot()).isEqualTo(BACK);
        assertThat(argumentCaptor.getValue().getFrontEquipped()).containsExactlyInAnyOrder(ShipConstants.LASER_ID, ShipConstants.LASER_ID, ShipConstants.LAUNCHER_ID);
        assertThat(argumentCaptor.getValue().getLeftEquipped()).containsExactlyInAnyOrder(ShipConstants.RIFLE_ID);
        assertThat(argumentCaptor.getValue().getRightEquipped()).containsExactlyInAnyOrder(ShipConstants.RIFLE_ID);
        assertThat(argumentCaptor.getValue().getBackEquipped()).containsExactlyInAnyOrder(ShipConstants.RIFLE_ID);
    }
}