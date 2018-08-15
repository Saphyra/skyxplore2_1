package skyxplore.controller.view.slot;

import static org.junit.Assert.assertEquals;
import static skyxplore.TestUtils.DATA_ITEM_BACK;
import static skyxplore.TestUtils.DATA_ITEM_FRONT;
import static skyxplore.TestUtils.DATA_ITEM_LEFT;
import static skyxplore.TestUtils.DATA_ITEM_RIGHT;
import static skyxplore.TestUtils.DEFENSE_SLOT_ID;
import static skyxplore.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.TestUtils.EQUIPPED_SLOT_BACK_SLOT;
import static skyxplore.TestUtils.EQUIPPED_SLOT_FRONT_SLOT;
import static skyxplore.TestUtils.EQUIPPED_SLOT_LEFT_SLOT;
import static skyxplore.TestUtils.EQUIPPED_SLOT_RIGHT_SLOT;
import static skyxplore.TestUtils.createEquippedSlot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SlotViewConverterTest {
    @InjectMocks
    private SlotViewConverter underTest;

    @Test
    public void tesConvertShouldReturnView() {
        //WHEN
        SlotView result = underTest.convertDomain(createEquippedSlot(DEFENSE_SLOT_ID));
        //THEN
        assertEquals(DEFENSE_SLOT_ID, result.getSlotId());
        assertEquals(EQUIPPED_SHIP_ID, result.getShipId());
        assertEquals(EQUIPPED_SLOT_FRONT_SLOT, result.getFrontSlot());
        assertEquals(EQUIPPED_SLOT_LEFT_SLOT, result.getLeftSlot());
        assertEquals(EQUIPPED_SLOT_RIGHT_SLOT, result.getRightSlot());
        assertEquals(EQUIPPED_SLOT_BACK_SLOT, result.getBackSlot());
        assertEquals(1, result.getFrontEquipped().size());
        assertEquals(1, result.getLeftEquipped().size());
        assertEquals(1, result.getRightEquipped().size());
        assertEquals(1, result.getBackEquipped().size());
        assertEquals(DATA_ITEM_FRONT, result.getFrontEquipped().get(0));
        assertEquals(DATA_ITEM_LEFT, result.getLeftEquipped().get(0));
        assertEquals(DATA_ITEM_RIGHT, result.getRightEquipped().get(0));
        assertEquals(DATA_ITEM_BACK, result.getBackEquipped().get(0));
    }
}
