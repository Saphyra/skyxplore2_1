package com.github.saphyra.skyxplore.slot.domain;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class EquippedSlotTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String EQUIPPED_SLOT_ID = "equipped_slot_id";
    private static final String ITEM_1 = "item_1";
    private static final String ITEM_2 = "item_2";
    private SkyXpCharacter character;

    @InjectMocks
    private EquippedSlot underTest;

    @Before
    public void init() {
        character = SkyXpCharacter.builder().characterId(CHARACTER_ID).build();

        underTest = EquippedSlot.builder()
            .slotId(EQUIPPED_SLOT_ID)
            .frontSlot(0)
            .leftSlot(0)
            .rightSlot(0)
            .backSlot(0)
            .build();
    }

    @Test
    public void testAddSlotShouldAdd() {
        //WHEN
        underTest.addSlot(2);
        //THEN
        assertThat(underTest.getFrontSlot()).isEqualTo(2);
        assertThat(underTest.getLeftSlot()).isEqualTo(2);
        assertThat(underTest.getRightSlot()).isEqualTo(2);
        assertThat(underTest.getBackSlot()).isEqualTo(2);
    }

    @Test
    public void testRemoveSlotShouldRemove() {
        //GIVEN
        underTest.setFrontSlot(2);
        underTest.setLeftSlot(2);
        underTest.setRightSlot(2);
        underTest.setBackSlot(2);

        List<String> equipments = Arrays.asList(ITEM_1, ITEM_2);
        underTest.addFront(equipments);
        underTest.addLeft(equipments);
        underTest.addRight(equipments);
        underTest.addBack(equipments);

        //WHEN
        underTest.removeSlot(character, 1);
        //THEN
        assertThat(character.getEquipments()).hasSize(4);
        assertThat(character.getEquipments()).contains(ITEM_1);

        assertThat(underTest.getFrontSlot()).isEqualTo(1);
        assertThat(underTest.getLeftSlot()).isEqualTo(1);
        assertThat(underTest.getRightSlot()).isEqualTo(1);
        assertThat(underTest.getBackSlot()).isEqualTo(1);

        assertThat(underTest.getFrontEquipped()).hasSize(1);
        assertThat(underTest.getLeftEquipped()).hasSize(1);
        assertThat(underTest.getRightEquipped()).hasSize(1);
        assertThat(underTest.getBackEquipped()).hasSize(1);
    }

    @Test(expected = BadRequestException.class)
    public void testAddFrontShouldThrowExceptionWhenFull() {
        //GIVEN
        underTest.setFrontSlot(1);
        //THEN
        underTest.addFront(ITEM_1);
        underTest.addFront(ITEM_1);
    }

    @Test
    public void testAddFrontShouldAdd() {
        //GIVEN
        underTest.setFrontSlot(1);
        //WHEN
        underTest.addFront(ITEM_1);
        //THEN
        assertThat(underTest.getFrontEquipped()).containsOnly(ITEM_1);
    }

    @Test
    public void testAddFrontsShouldAdd() {
        //GIVEN
        underTest.setFrontSlot(2);
        List<String> elements = Arrays.asList(ITEM_1, ITEM_2);
        //WHEN
        underTest.addFront(elements);
        //THEN
        assertThat(underTest.getFrontEquipped()).containsExactly(ITEM_1, ITEM_2);
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveFrontShouldThrowExceptionWhenDoesNotContains() {
        underTest.removeFront(ITEM_1);
    }

    @Test
    public void testRemoveFrontShouldRemove() {
        //GIVEN
        underTest.setFrontSlot(2);
        underTest.addFront(ITEM_1);
        underTest.addFront(ITEM_2);
        //WHEN
        underTest.removeFront(ITEM_2);
        //THEN
        assertThat(underTest.getFrontEquipped()).containsExactly(ITEM_1);
    }

    @Test
    public void testGetFrontShouldReturnCopy() {
        //GIVEN
        underTest.setFrontSlot(1);
        underTest.addFront(ITEM_1);
        //WHEN
        List<String> result = underTest.getFrontEquipped();
        result.add(ITEM_2);
        //THEN
        assertThat(underTest.getFrontEquipped()).containsExactly(ITEM_1);
    }

    @Test(expected = BadRequestException.class)
    public void testAddLeftShouldThrowExceptionWhenFull() {
        //GIVEN
        underTest.setLeftSlot(1);
        //THEN
        underTest.addLeft(ITEM_1);
        underTest.addLeft(ITEM_1);
    }

    @Test
    public void testAddLeftShouldAdd() {
        //GIVEN
        underTest.setLeftSlot(1);
        //WHEN
        underTest.addLeft(ITEM_1);
        //THEN
        assertThat(underTest.getLeftEquipped()).containsExactly(ITEM_1);
    }

    @Test
    public void testAddLeftsShouldAdd() {
        //GIVEN
        underTest.setLeftSlot(2);
        List<String> elements = Arrays.asList(ITEM_1, ITEM_2);
        //WHEN
        underTest.addLeft(elements);
        //THEN
        assertThat(underTest.getLeftEquipped()).containsExactly(ITEM_1, ITEM_2);
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveLeftShouldThrowExceptionWhenDoesNotConatins() {
        underTest.removeLeft(ITEM_1);
    }

    @Test
    public void testRemoveLeftShouldRemove() {
        //GIVEN
        underTest.setLeftSlot(2);
        underTest.addLeft(ITEM_1);
        underTest.addLeft(ITEM_2);
        //WHEN
        underTest.removeLeft(ITEM_2);
        //THEN
        assertThat(underTest.getLeftEquipped()).containsExactly(ITEM_1);
    }

    @Test
    public void testGetLeftShouldReturnCopy() {
        //GIVEN
        underTest.setLeftSlot(1);
        underTest.addLeft(ITEM_1);
        //WHEN
        List<String> result = underTest.getLeftEquipped();
        result.add(ITEM_2);
        //THEN
        assertThat(underTest.getLeftEquipped()).containsExactly(ITEM_1);
    }

    @Test(expected = BadRequestException.class)
    public void testAddRightShouldThrowExceptionWhenFull() {
        //GIVEN
        underTest.setRightSlot(1);
        //THEN
        underTest.addRight(ITEM_1);
        underTest.addRight(ITEM_1);
    }

    @Test
    public void testAddRightShouldAdd() {
        //GIVEN
        underTest.setRightSlot(1);
        //WHEN
        underTest.addRight(ITEM_1);
        //THEN
        assertThat(underTest.getRightEquipped()).containsExactly(ITEM_1);
    }

    @Test
    public void testAddRightsShouldAdd() {
        //GIVEN
        underTest.setRightSlot(2);
        List<String> elements = Arrays.asList(ITEM_1, ITEM_2);
        //WHEN
        underTest.addRight(elements);
        //THEN
        assertThat(underTest.getRightEquipped()).containsExactly(ITEM_1, ITEM_2);
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveRightShouldThrowExceptionWhenDoesNotContains() {
        underTest.removeRight(ITEM_1);
    }

    @Test
    public void testRemoveRightShouldRemove() {
        //GIVEN
        underTest.setRightSlot(2);
        underTest.addRight(ITEM_1);
        underTest.addRight(ITEM_2);
        //WHEN
        underTest.removeRight(ITEM_2);
        //THEN
        assertThat(underTest.getRightEquipped()).containsExactly(ITEM_1);
    }

    @Test
    public void testGetRightShouldReturnCopy() {
        //GIVEN
        underTest.setRightSlot(1);
        underTest.addRight(ITEM_1);
        //WHEN
        List<String> result = underTest.getRightEquipped();
        result.add(ITEM_2);
        //THEN
        assertThat(underTest.getRightEquipped()).containsExactly(ITEM_1);
    }

    @Test(expected = BadRequestException.class)
    public void testAddBackShouldThrowExceptionWhenFull() {
        //GIVEN
        underTest.setBackSlot(1);
        //THEN
        underTest.addBack(ITEM_1);
        underTest.addBack(ITEM_1);
    }

    @Test
    public void testAddBackShouldAdd() {
        //GIVEN
        underTest.setBackSlot(1);
        //WHEN
        underTest.addBack(ITEM_1);
        //THEN
        assertThat(underTest.getBackEquipped()).containsExactly(ITEM_1);
    }

    @Test
    public void testAddBacksShouldAdd() {
        //GIVEN
        underTest.setBackSlot(2);
        List<String> elements = Arrays.asList(ITEM_1, ITEM_2);
        //WHEN
        underTest.addBack(elements);
        //THEN
        assertThat(underTest.getBackEquipped()).containsExactly(ITEM_1, ITEM_2);
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveBackShouldThrowExceptionWhenDoesNotContains() {
        underTest.removeBack(ITEM_1);
    }

    @Test
    public void testRemoveBackShouldRemove() {
        //GIVEN
        underTest.setBackSlot(2);
        underTest.addBack(ITEM_1);
        underTest.addBack(ITEM_2);
        //WHEN
        underTest.removeBack(ITEM_2);
        //THEN
        assertThat(underTest.getBackEquipped()).containsExactly(ITEM_1);
    }

    @Test
    public void testGetBackShouldReturnCopy() {
        //GIVEN
        underTest.setBackSlot(1);
        underTest.addBack(ITEM_1);
        //WHEN
        List<String> result = underTest.getBackEquipped();
        result.add(ITEM_2);
        //THEN
        assertThat(underTest.getBackEquipped()).containsExactly(ITEM_1);
    }
}
