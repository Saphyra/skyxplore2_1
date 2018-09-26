package skyxplore.dataaccess.db;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_ID;
import static skyxplore.testutil.TestUtils.createEquippedSlot;
import static skyxplore.testutil.TestUtils.createSlotEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.dataaccess.db.repository.SlotRepository;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.domain.slot.SlotConverter;
import skyxplore.domain.slot.SlotEntity;

@RunWith(MockitoJUnitRunner.class)
public class SlotDaoTest {
    @Mock
    private SlotConverter slotConverter;

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private SlotDao underTest;

    @Test
    public void testDeleteByShipIdShouldCallRepository(){
        //WHEN
        underTest.deleteByShipId(EQUIPPED_SHIP_ID);
        //THEN
        verify(slotRepository).deleteByShipId(EQUIPPED_SHIP_ID);
    }

    @Test
    public void tesGetByIdShouldConvertAndReturn(){
        //GIVEN
        SlotEntity entity = createSlotEntity();
        when(slotRepository.getOne(EQUIPPED_SLOT_ID)).thenReturn(entity);

        EquippedSlot equippedSlot = createEquippedSlot();
        when(slotConverter.convertEntity(entity)).thenReturn(equippedSlot);
        //WHEN
        EquippedSlot result = underTest.getById(EQUIPPED_SLOT_ID);
        //THEN
        verify(slotRepository).getOne(EQUIPPED_SLOT_ID);
        verify(slotConverter).convertEntity(entity);
        assertEquals(equippedSlot, result);
    }
}