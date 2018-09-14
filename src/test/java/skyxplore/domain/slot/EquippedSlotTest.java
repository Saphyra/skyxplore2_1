package skyxplore.domain.slot;

import static org.junit.Assert.assertEquals;
import static skyxplore.testutil.TestUtils.CHARACTER_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_ID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.domain.character.SkyXpCharacter;

@RunWith(MockitoJUnitRunner.class)
public class EquippedSlotTest {
    @Mock
    private SkyXpCharacter character;

    @InjectMocks
    private EquippedSlot underTest;

    @Before
    public void init() {
        character = new SkyXpCharacter();
        character.setCharacterId(CHARACTER_ID);

        underTest = new EquippedSlot();
        underTest.setSlotId(EQUIPPED_SLOT_ID);
        underTest.setBackSlot(0);
        underTest.setFrontSlot(0);
        underTest.setLeftSlot(0);
        underTest.setRightSlot(0);
    }

    @Test
    public void testAddSlotShouldAdd() {
        //WHEN
        underTest.addSlot(2);
        //THEN
        assertEquals((Integer) 2, underTest.getFrontSlot());
        assertEquals((Integer) 2, underTest.getBackSlot());
        assertEquals((Integer) 2, underTest.getLeftSlot());
        assertEquals((Integer) 2, underTest.getRightSlot());
    }
}
