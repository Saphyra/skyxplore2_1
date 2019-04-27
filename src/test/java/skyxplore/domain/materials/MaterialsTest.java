package skyxplore.domain.materials;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.exception.NotEnoughMaterialsException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static skyxplore.testutil.TestUtils.MATERIAL_AMOUNT;
import static skyxplore.testutil.TestUtils.MATERIAL_ID;
import static skyxplore.testutil.TestUtils.MATERIAL_KEY;

@RunWith(MockitoJUnitRunner.class)
public class MaterialsTest {

    private Materials underTest;

    @Before
    public void setUp() {
        underTest = new Materials();
    }

    @Test
    public void testGetShouldReturnZeroIfNotContains() {
        //WHEN
        Integer result = underTest.get(MATERIAL_ID);
        //THEN
        assertEquals((Integer) 0, result);
    }

    @Test
    public void testGetShouldReturnValue() {
        //GIVEN
        underTest.addMaterial(MATERIAL_ID, MATERIAL_AMOUNT);
        //WHEN
        Integer result = underTest.get(MATERIAL_ID);
        //THEN
        assertEquals(MATERIAL_AMOUNT, result);
    }

    @Test
    public void testAddMaterialShouldIncrement() {
        //GIVEN
        underTest.put(MATERIAL_ID, MATERIAL_AMOUNT);
        //WHEN
        underTest.addMaterial(MATERIAL_ID, MATERIAL_AMOUNT);
        //THEN
        assertEquals(MATERIAL_AMOUNT * 2, (int) underTest.get(MATERIAL_ID));
    }

    @Test(expected = NotEnoughMaterialsException.class)
    public void testRemoveMaterialShouldThrowExceptionWhenNotEnough() {
        underTest.removeMaterial(MATERIAL_ID, MATERIAL_AMOUNT);
    }

    @Test
    public void testRemoveMaterialShouldRemove() {
        //GIVEN
        underTest.addMaterial(MATERIAL_ID, 2 * MATERIAL_AMOUNT);
        //WHEN
        underTest.removeMaterial(MATERIAL_ID, MATERIAL_AMOUNT);
        //THEN
        assertEquals(MATERIAL_AMOUNT, underTest.get(MATERIAL_ID));
    }

    @Test
    public void testPutShouldIncrement() {
        //GIVEN
        //WHEN
        underTest.put(MATERIAL_ID, MATERIAL_AMOUNT);
        //THEN
        assertEquals(MATERIAL_AMOUNT, underTest.get(MATERIAL_ID));
    }

    @Test
    public void testPutAllShouldPut() {
        //GIVEN
        Map<String, Integer> map = new HashMap<>();
        map.put(MATERIAL_ID, MATERIAL_AMOUNT);
        map.put(MATERIAL_KEY, MATERIAL_AMOUNT);
        //WHEN
        underTest.putAll(map);
        //THEN
        assertEquals(MATERIAL_AMOUNT, underTest.get(MATERIAL_ID));
        assertEquals(MATERIAL_AMOUNT, underTest.get(MATERIAL_KEY));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveShouldThrowException() {
        underTest.remove(MATERIAL_ID);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClearShouldThrowException() {
        underTest.clear();
    }
}