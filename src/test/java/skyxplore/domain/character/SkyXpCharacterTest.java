package skyxplore.domain.character;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.exception.NotEnoughMoneyException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static skyxplore.testutil.TestUtils.CHARACTER_ENCRYPTED_EQUIPMENTS;
import static skyxplore.testutil.TestUtils.CHARACTER_EQUIPMENT;
import static skyxplore.testutil.TestUtils.CHARACTER_MONEY;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpCharacterTest {
    private SkyXpCharacter underTest;

    @Before
    public void init() {
        underTest = new SkyXpCharacter();
    }

    @Test
    public void testAddEquipmentShouldAdd() {
        //WHEN
        underTest.addEquipment(CHARACTER_EQUIPMENT);
        //THEN
        assertEquals(CHARACTER_EQUIPMENT, underTest.getEquipments().get(0));
    }

    @Test
    public void testAddEquipmentsShouldAdd() {
        //GIVEN
        List<String> equipmentList = Arrays.asList(CHARACTER_EQUIPMENT, CHARACTER_ENCRYPTED_EQUIPMENTS);
        //WHEN
        underTest.addEquipments(equipmentList);
        //THEN
        assertTrue(underTest.getEquipments().contains(CHARACTER_EQUIPMENT));
        assertTrue(underTest.getEquipments().contains(CHARACTER_ENCRYPTED_EQUIPMENTS));
    }

    @Test
    public void testAddEquipmentsXTimesShouldAdd() {
        //GIVEN

        //WHEN
        underTest.addEquipments(CHARACTER_EQUIPMENT, 2);
        //THEN
        assertEquals(2, underTest.getEquipments().size());
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveEquipmentShouldThrowExceptionWhenNotContains() {
        underTest.removeEquipment(CHARACTER_EQUIPMENT);
    }

    @Test
    public void testRemoveEquipmentShouldRemove() {
        //GIVEN
        underTest.addEquipments(Arrays.asList(CHARACTER_EQUIPMENT, CHARACTER_ENCRYPTED_EQUIPMENTS));
        //WHEN
        underTest.removeEquipment(CHARACTER_ENCRYPTED_EQUIPMENTS);
        //THEN
        assertTrue(underTest.getEquipments().contains(CHARACTER_EQUIPMENT));
        assertFalse(underTest.getEquipments().contains(CHARACTER_ENCRYPTED_EQUIPMENTS));
    }

    @Test
    public void testAddMoneyShouldAdd() {
        //WHEN
        underTest.addMoney(CHARACTER_MONEY);
        //THEN
        assertEquals(CHARACTER_MONEY, underTest.getMoney());
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testBuyEquipmentsShouldThrowExceptionWhenNotEnoughMoney() {
        underTest.buyEquipments(new HashMap<>(), CHARACTER_MONEY);
    }

    @Test
    public void testBuyEquipmentsShouldSpendMoneyAndAddEquipments() {
        //GIVEN
        underTest.addMoney(15);
        Map<String, Integer> items = new HashMap<>();
        items.put(CHARACTER_EQUIPMENT, 2);
        items.put(CHARACTER_ENCRYPTED_EQUIPMENTS, 1);
        //WHEN
        underTest.buyEquipments(items, CHARACTER_MONEY);
        //THEN
        assertEquals((Integer) 5, underTest.getMoney());
        assertEquals(3, underTest.getEquipments().size());
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testSpendMoneyShouldThrowExceptionWhenNotEnoughMoney() {
        underTest.spendMoney(CHARACTER_MONEY);
    }

    @Test
    public void testSpendMoneyShouldReduceMoney() {
        //GIVEN
        underTest.addMoney(CHARACTER_MONEY);
        //WHEN
        underTest.spendMoney(5);
        //THEN
        assertEquals((Integer) 5, underTest.getMoney());
    }

    @Test
    public void testGetEquipmentsShouldReturnCopy(){
        //GIVEN
        underTest.addEquipment(CHARACTER_EQUIPMENT);
        //WHEN
        List<String> result = underTest.getEquipments();
        result.add(CHARACTER_ENCRYPTED_EQUIPMENTS);
        //THEN
        assertEquals(2, result.size());
        assertEquals(1, underTest.getEquipments().size());
    }
}
