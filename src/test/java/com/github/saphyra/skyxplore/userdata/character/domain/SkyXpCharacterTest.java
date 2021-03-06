package com.github.saphyra.skyxplore.userdata.character.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.common.exception.NotEnoughMoneyException;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpCharacterTest {
    private static final String EQUIPMENT_1 = "equipment_1";
    private static final String EQUIPMENT_2 = "equipment_2";
    private static final Integer MONEY = 5;

    private SkyXpCharacter underTest;

    @Before
    public void init() {
        underTest = SkyXpCharacter.builder()
            .characterId("")
            .characterName("")
            .userId("")
            .build();
    }

    @Test
    public void testAddEquipmentShouldAdd() {
        //WHEN
        underTest.addEquipment(EQUIPMENT_1);
        //THEN
        assertThat(underTest.getEquipments()).containsOnly(EQUIPMENT_1);
    }

    @Test
    public void testAddEquipmentsShouldAdd() {
        //GIVEN
        List<String> equipmentList = Arrays.asList(EQUIPMENT_1, EQUIPMENT_2);
        //WHEN
        underTest.addEquipments(equipmentList);
        //THEN
        assertThat(underTest.getEquipments())
            .contains(EQUIPMENT_1)
            .contains(EQUIPMENT_2);
    }

    @Test
    public void testAddEquipmentsXTimesShouldAdd() {
        //GIVEN

        //WHEN
        underTest.addEquipments(EQUIPMENT_1, 2);
        //THEN
        assertThat(underTest.getEquipments()).hasSize(2)
            .containsExactly(EQUIPMENT_1, EQUIPMENT_1);
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveEquipmentShouldThrowExceptionWhenNotContains() {
        underTest.removeEquipment(EQUIPMENT_1);
    }

    @Test
    public void testRemoveEquipmentShouldRemove() {
        //GIVEN
        underTest.addEquipments(Arrays.asList(EQUIPMENT_1, EQUIPMENT_2));
        //WHEN
        underTest.removeEquipment(EQUIPMENT_2);
        //THEN
        assertThat(underTest.getEquipments()).containsOnly(EQUIPMENT_1);
    }

    @Test
    public void testAddMoneyShouldAdd() {
        //WHEN
        underTest.addMoney(MONEY);
        //THEN
        assertThat(underTest.getMoney()).isEqualTo(MONEY);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testBuyEquipmentsShouldThrowExceptionWhenNotEnoughMoney() {
        underTest.buyEquipments(new HashMap<>(), MONEY);
    }

    @Test
    public void testBuyEquipmentsShouldSpendMoneyAndAddEquipments() {
        //GIVEN
        underTest.addMoney(15);
        Map<String, Integer> items = new HashMap<>();
        items.put(EQUIPMENT_1, 2);
        items.put(EQUIPMENT_2, 1);
        //WHEN
        underTest.buyEquipments(items, MONEY);
        //THEN
        assertThat(underTest.getMoney()).isEqualTo(10);
        assertThat(underTest.getEquipments())
            .hasSize(3)
            .containsExactly(EQUIPMENT_1, EQUIPMENT_1, EQUIPMENT_2);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testSpendMoneyShouldThrowExceptionWhenNotEnoughMoney() {
        underTest.spendMoney(MONEY);
    }

    @Test
    public void testSpendMoneyShouldReduceMoney() {
        //GIVEN
        underTest.addMoney(MONEY);
        //WHEN
        underTest.spendMoney(3);
        //THEN
        assertThat(underTest.getMoney()).isEqualTo(2);
    }

    @Test
    public void testGetEquipmentsShouldReturnCopy() {
        //GIVEN
        underTest.addEquipment(EQUIPMENT_1);
        //WHEN
        List<String> result = underTest.getEquipments();
        result.add(EQUIPMENT_2);
        //THEN
        assertThat(underTest.getEquipments()).containsOnly(EQUIPMENT_1);
    }
}
