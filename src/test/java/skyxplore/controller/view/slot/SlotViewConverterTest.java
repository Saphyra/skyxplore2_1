package skyxplore.controller.view.slot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static skyxplore.testutil.TestUtils.DATA_ITEM_BACK;
import static skyxplore.testutil.TestUtils.DATA_ITEM_FRONT;
import static skyxplore.testutil.TestUtils.DATA_ITEM_LEFT;
import static skyxplore.testutil.TestUtils.DATA_ITEM_RIGHT;
import static skyxplore.testutil.TestUtils.DEFENSE_SLOT_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_BACK_SLOT;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_FRONT_SLOT;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_LEFT_SLOT;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_RIGHT_SLOT;
import static skyxplore.testutil.TestUtils.createEquippedSlot;

@RunWith(MockitoJUnitRunner.class)
public class SlotViewConverterTest {
    @InjectMocks
    private SlotViewConverter underTest;

    @Test
    public void tesConvertShouldReturnView() {
        //WHEN
        SlotView result = underTest.convertDomain(createEquippedSlot(DEFENSE_SLOT_ID));
        //THEN
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
