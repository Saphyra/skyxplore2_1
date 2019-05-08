package com.github.saphyra.skyxplore.slot;

import com.github.saphyra.skyxplore.common.ShipConstants;
import com.github.saphyra.skyxplore.gamedata.entity.Ship;
import com.github.saphyra.skyxplore.gamedata.entity.Slot;
import com.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.slot.repository.SlotDao;
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
public class DefenseSlotCreatorServiceTest {
    private static final Integer FRONT = 4;
    private static final Integer SIDE = 2;
    private static final Integer BACK = 3;
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
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
    private DefenseSlotCreatorService underTest;

    @Test
    public void createSlot() {
        //GIVEN
        given(shipService.get(ShipConstants.STARTER_SHIP_ID)).willReturn(ship);
        Slot slot = new Slot();
        slot.setFront(FRONT);
        slot.setSide(SIDE);
        slot.setBack(BACK);
        given(ship.getDefense()).willReturn(slot);
        given(idGenerator.generateRandomId()).willReturn(DEFENSE_SLOT_ID);
        //WHEN
        String result = underTest.createSlot(SHIP_ID);
        //THEN
        assertThat(result).isEqualTo(DEFENSE_SLOT_ID);
        ArgumentCaptor<EquippedSlot> argumentCaptor = ArgumentCaptor.forClass(EquippedSlot.class);
        verify(slotDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getSlotId()).isEqualTo(DEFENSE_SLOT_ID);
        assertThat(argumentCaptor.getValue().getShipId()).isEqualTo(SHIP_ID);
        assertThat(argumentCaptor.getValue().getFrontSlot()).isEqualTo(FRONT);
        assertThat(argumentCaptor.getValue().getLeftSlot()).isEqualTo(SIDE);
        assertThat(argumentCaptor.getValue().getRightSlot()).isEqualTo(SIDE);
        assertThat(argumentCaptor.getValue().getBackSlot()).isEqualTo(BACK);
        assertThat(argumentCaptor.getValue().getFrontEquipped()).containsExactlyInAnyOrder(ShipConstants.SHIELD_ID, ShipConstants.SHIELD_ID, ShipConstants.ARMOR_ID, ShipConstants.ARMOR_ID);
        assertThat(argumentCaptor.getValue().getLeftEquipped()).containsExactlyInAnyOrder(ShipConstants.SHIELD_ID, ShipConstants.ARMOR_ID);
        assertThat(argumentCaptor.getValue().getRightEquipped()).containsExactlyInAnyOrder(ShipConstants.SHIELD_ID, ShipConstants.ARMOR_ID);
        assertThat(argumentCaptor.getValue().getBackEquipped()).containsExactlyInAnyOrder(ShipConstants.SHIELD_ID, ShipConstants.ARMOR_ID);
    }
}