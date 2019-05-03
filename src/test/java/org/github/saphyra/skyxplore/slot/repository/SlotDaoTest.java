package org.github.saphyra.skyxplore.slot.repository;

import org.github.saphyra.skyxplore.event.ShipDeletedEvent;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SlotDaoTest {
    private static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
    private static final String EQUIPPED_SLOT_ID = "equipped_slot_id";
    @Mock
    private SlotConverter slotConverter;

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private SlotDao underTest;

    @Test
    public void testDeleteByShipIdShouldCallRepository() {
        //WHEN
        underTest.deleteByShipId(new ShipDeletedEvent(EQUIPPED_SHIP_ID));
        //THEN
        verify(slotRepository).deleteByShipId(EQUIPPED_SHIP_ID);
    }

    @Test
    public void tesGetByIdShouldConvertAndReturn() {
        //GIVEN
        SlotEntity entity = SlotEntity.builder().build();
        when(slotRepository.getOne(EQUIPPED_SLOT_ID)).thenReturn(entity);

        EquippedSlot equippedSlot = EquippedSlot.builder().build();
        when(slotConverter.convertEntity(entity)).thenReturn(equippedSlot);
        //WHEN
        EquippedSlot result = underTest.getById(EQUIPPED_SLOT_ID);
        //THEN
        verify(slotRepository).getOne(EQUIPPED_SLOT_ID);
        verify(slotConverter).convertEntity(entity);
        assertThat(result).isEqualTo(equippedSlot);
    }
}