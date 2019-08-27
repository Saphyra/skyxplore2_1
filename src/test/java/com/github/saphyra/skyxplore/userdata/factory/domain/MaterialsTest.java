package com.github.saphyra.skyxplore.userdata.factory.domain;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.PaymentRequiredException;
import com.github.saphyra.skyxplore.common.ErrorCode;

@RunWith(MockitoJUnitRunner.class)
public class MaterialsTest {
    private static final String MATERIAL_ID_1 = "material_id_1";
    private static final Integer AMOUNT = 3;
    private static final String MATERIAL_ID_2 = "material_id_2";

    private Materials underTest;

    @Before
    public void setUp() {
        underTest = new Materials();
    }

    @Test
    public void testGetShouldReturnZeroIfNotContains() {
        //WHEN
        Integer result = underTest.get(MATERIAL_ID_1);
        //THEN
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testGetShouldReturnValue() {
        //GIVEN
        underTest.addMaterial(MATERIAL_ID_1, AMOUNT);
        //WHEN
        Integer result = underTest.get(MATERIAL_ID_1);
        //THEN
        assertThat(result).isEqualTo(AMOUNT);
    }

    @Test
    public void testAddMaterialShouldIncrement() {
        //GIVEN
        underTest.put(MATERIAL_ID_1, AMOUNT);
        //WHEN
        underTest.addMaterial(MATERIAL_ID_1, AMOUNT);
        //THEN
        assertThat(underTest.get(MATERIAL_ID_1)).isEqualTo(AMOUNT + AMOUNT);
    }

    @Test
    public void testRemoveMaterialShouldThrowExceptionWhenNotEnough() {
        //GIVEN
        underTest.addMaterial(MATERIAL_ID_1, AMOUNT - 1);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.removeMaterial(MATERIAL_ID_1, AMOUNT));
        //THEN
        verifyException(ex, PaymentRequiredException.class, ErrorCode.NOT_ENOUGH_MATERIALS);
    }

    @Test
    public void testRemoveMaterialShouldRemove() {
        //GIVEN
        underTest.addMaterial(MATERIAL_ID_1, 2 * AMOUNT);
        //WHEN
        underTest.removeMaterial(MATERIAL_ID_1, AMOUNT);
        //THEN
        assertThat(underTest.get(MATERIAL_ID_1)).isEqualTo(AMOUNT);
    }

    @Test
    public void testPutShouldIncrement() {
        //WHEN
        underTest.put(MATERIAL_ID_1, AMOUNT);
        //THEN
        assertThat(underTest.get(MATERIAL_ID_1)).isEqualTo(AMOUNT);
    }

    @Test
    public void testPutAllShouldPut() {
        //GIVEN
        Map<String, Integer> map = new HashMap<>();
        map.put(MATERIAL_ID_1, AMOUNT);
        map.put(MATERIAL_ID_2, AMOUNT + 1);
        //WHEN
        underTest.putAll(map);
        //THEN
        assertThat(underTest.get(MATERIAL_ID_1)).isEqualTo(AMOUNT);
        assertThat(underTest.get(MATERIAL_ID_2)).isEqualTo(AMOUNT + 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveShouldThrowException() {
        underTest.remove(MATERIAL_ID_1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClearShouldThrowException() {
        underTest.clear();
    }
}