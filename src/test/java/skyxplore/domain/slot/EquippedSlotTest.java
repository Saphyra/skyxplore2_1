package skyxplore.domain.slot;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.domain.character.SkyXpCharacter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.DATA_CONNECTOR;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_ID;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class EquippedSlotTest {
    private SkyXpCharacter character;

    @InjectMocks
    private EquippedSlot underTest;

    @Before
    public void init() {
        character = new SkyXpCharacter();
        character.setCharacterId(CHARACTER_ID_1);

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

    @Test
    public void testRemoveSlotShouldRemove() {
        //GIVEN
        underTest.setFrontSlot(2);
        underTest.setLeftSlot(2);
        underTest.setRightSlot(2);
        underTest.setBackSlot(2);

        List<String> equipments = Arrays.asList(DATA_ELEMENT, DATA_ELEMENT);
        underTest.addFront(equipments);
        underTest.addLeft(equipments);
        underTest.addRight(equipments);
        underTest.addBack(equipments);

        //WHEN
        underTest.removeSlot(character, 1);
        //THEN
        assertEquals(4, character.getEquipments().size());
        assertTrue(character.getEquipments().contains(DATA_ELEMENT));
        assertEquals((Integer) 1, underTest.getFrontSlot());
        assertEquals((Integer) 1, underTest.getLeftSlot());
        assertEquals((Integer) 1, underTest.getRightSlot());
        assertEquals((Integer) 1, underTest.getBackSlot());
        assertEquals(1, underTest.getFrontEquipped().size());
        assertEquals(1, underTest.getLeftEquipped().size());
        assertEquals(1, underTest.getRightEquipped().size());
        assertEquals(1, underTest.getBackEquipped().size());
    }

    @Test(expected = BadRequestException.class)
    public void testAddFrontShouldThrowExceptionWhenFull() {
        //GIVEN
        underTest.setFrontSlot(1);
        //THEN
        underTest.addFront(DATA_ELEMENT);
        underTest.addFront(DATA_ELEMENT);
    }

    @Test
    public void testAddFrontShouldAdd() {
        //GIVEN
        underTest.setFrontSlot(1);
        //WHEN
        underTest.addFront(DATA_ELEMENT);
        //THEN
        assertTrue(underTest.getFrontEquipped().contains(DATA_ELEMENT));
    }

    @Test
    public void testAddFrontsShouldAdd() {
        //GIVEN
        underTest.setFrontSlot(2);
        List<String> elements = Arrays.asList(DATA_ELEMENT, DATA_ELEMENT);
        //WHEN
        underTest.addFront(elements);
        //THEN
        assertEquals(2, underTest.getFrontEquipped().size());
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveFrontShouldThrowExceptionWhenDoesNotConatins() {
        underTest.removeFront(DATA_ELEMENT);
    }

    @Test
    public void testRemoveFrontShouldRemove() {
        //GIVEN
        underTest.setFrontSlot(2);
        underTest.addFront(DATA_ELEMENT);
        underTest.addFront(DATA_CONNECTOR);
        //WHEN
        underTest.removeFront(DATA_CONNECTOR);
        //THEN
        assertTrue(underTest.getFrontEquipped().contains(DATA_ELEMENT));
        assertFalse(underTest.getFrontEquipped().contains(DATA_CONNECTOR));
    }

    @Test
    public void testGetFrontShouldReturnCopy() {
        //GIVEN
        underTest.setFrontSlot(1);
        underTest.addFront(DATA_ELEMENT);
        //WHEN
        List<String> result = underTest.getFrontEquipped();
        result.add(DATA_CONNECTOR);
        //THEN
        assertEquals(1, underTest.getFrontEquipped().size());
    }

    @Test(expected = BadRequestException.class)
    public void testAddLeftShouldThrowExceptionWhenFull() {
        //GIVEN
        underTest.setLeftSlot(1);
        //THEN
        underTest.addLeft(DATA_ELEMENT);
        underTest.addLeft(DATA_ELEMENT);
    }

    @Test
    public void testAddLeftShouldAdd() {
        //GIVEN
        underTest.setLeftSlot(1);
        //WHEN
        underTest.addLeft(DATA_ELEMENT);
        //THEN
        assertTrue(underTest.getLeftEquipped().contains(DATA_ELEMENT));
    }

    @Test
    public void testAddLeftsShouldAdd() {
        //GIVEN
        underTest.setLeftSlot(2);
        List<String> elements = Arrays.asList(DATA_ELEMENT, DATA_ELEMENT);
        //WHEN
        underTest.addLeft(elements);
        //THEN
        assertEquals(2, underTest.getLeftEquipped().size());
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveLeftShouldThrowExceptionWhenDoesNotConatins() {
        underTest.removeLeft(DATA_ELEMENT);
    }

    @Test
    public void testRemoveLeftShouldRemove() {
        //GIVEN
        underTest.setLeftSlot(2);
        underTest.addLeft(DATA_ELEMENT);
        underTest.addLeft(DATA_CONNECTOR);
        //WHEN
        underTest.removeLeft(DATA_CONNECTOR);
        //THEN
        assertTrue(underTest.getLeftEquipped().contains(DATA_ELEMENT));
        assertFalse(underTest.getLeftEquipped().contains(DATA_CONNECTOR));
    }

    @Test
    public void testGetLeftShouldReturnCopy() {
        //GIVEN
        underTest.setLeftSlot(1);
        underTest.addLeft(DATA_ELEMENT);
        //WHEN
        List<String> result = underTest.getLeftEquipped();
        result.add(DATA_CONNECTOR);
        //THEN
        assertEquals(1, underTest.getLeftEquipped().size());
    }

    @Test(expected = BadRequestException.class)
    public void testAddRightShouldThrowExceptionWhenFull() {
        //GIVEN
        underTest.setRightSlot(1);
        //THEN
        underTest.addRight(DATA_ELEMENT);
        underTest.addRight(DATA_ELEMENT);
    }

    @Test
    public void testAddRightShouldAdd() {
        //GIVEN
        underTest.setRightSlot(1);
        //WHEN
        underTest.addRight(DATA_ELEMENT);
        //THEN
        assertTrue(underTest.getRightEquipped().contains(DATA_ELEMENT));
    }

    @Test
    public void testAddRightsShouldAdd() {
        //GIVEN
        underTest.setRightSlot(2);
        List<String> elements = Arrays.asList(DATA_ELEMENT, DATA_ELEMENT);
        //WHEN
        underTest.addRight(elements);
        //THEN
        assertEquals(2, underTest.getRightEquipped().size());
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveRightShouldThrowExceptionWhenDoesNotConatins() {
        underTest.removeRight(DATA_ELEMENT);
    }

    @Test
    public void testRemoveRightShouldRemove() {
        //GIVEN
        underTest.setRightSlot(2);
        underTest.addRight(DATA_ELEMENT);
        underTest.addRight(DATA_CONNECTOR);
        //WHEN
        underTest.removeRight(DATA_CONNECTOR);
        //THEN
        assertTrue(underTest.getRightEquipped().contains(DATA_ELEMENT));
        assertFalse(underTest.getRightEquipped().contains(DATA_CONNECTOR));
    }

    @Test
    public void testGetRightShouldReturnCopy() {
        //GIVEN
        underTest.setRightSlot(1);
        underTest.addRight(DATA_ELEMENT);
        //WHEN
        List<String> result = underTest.getRightEquipped();
        result.add(DATA_CONNECTOR);
        //THEN
        assertEquals(1, underTest.getRightEquipped().size());
    }

    @Test(expected = BadRequestException.class)
    public void testAddBackShouldThrowExceptionWhenFull() {
        //GIVEN
        underTest.setBackSlot(1);
        //THEN
        underTest.addBack(DATA_ELEMENT);
        underTest.addBack(DATA_ELEMENT);
    }

    @Test
    public void testAddBackShouldAdd() {
        //GIVEN
        underTest.setBackSlot(1);
        //WHEN
        underTest.addBack(DATA_ELEMENT);
        //THEN
        assertTrue(underTest.getBackEquipped().contains(DATA_ELEMENT));
    }

    @Test
    public void testAddBacksShouldAdd() {
        //GIVEN
        underTest.setBackSlot(2);
        List<String> elements = Arrays.asList(DATA_ELEMENT, DATA_ELEMENT);
        //WHEN
        underTest.addBack(elements);
        //THEN
        assertEquals(2, underTest.getBackEquipped().size());
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveBackShouldThrowExceptionWhenDoesNotConatins() {
        underTest.removeBack(DATA_ELEMENT);
    }

    @Test
    public void testRemoveBackShouldRemove() {
        //GIVEN
        underTest.setBackSlot(2);
        underTest.addBack(DATA_ELEMENT);
        underTest.addBack(DATA_CONNECTOR);
        //WHEN
        underTest.removeBack(DATA_CONNECTOR);
        //THEN
        assertTrue(underTest.getBackEquipped().contains(DATA_ELEMENT));
        assertFalse(underTest.getBackEquipped().contains(DATA_CONNECTOR));
    }

    @Test
    public void testGetBackShouldReturnCopy() {
        //GIVEN
        underTest.setBackSlot(1);
        underTest.addBack(DATA_ELEMENT);
        //WHEN
        List<String> result = underTest.getBackEquipped();
        result.add(DATA_CONNECTOR);
        //THEN
        assertEquals(1, underTest.getBackEquipped().size());
    }
}
