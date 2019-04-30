package org.github.saphyra.skyxplore.slot.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;

@RunWith(MockitoJUnitRunner.class)
public class SlotConverterTest {
    private static final String EQUIPPED_SLOT_ID = "equipped_slot_id";
    private static final String ENCRYPTED_SLOT = "encrypted_slot";
    private static final String ENCRYPTED_EQUIPPED_ITEMS = "encrypted_equipped_items";
    private static final String EQUIPPED_ITEMS = "equipped_items";
    private static final String SHIP_ID = "ship_id";
    private static final Integer EQUIPPED_SLOT = 3;
    private static final String EQUIPPED_ITEM = "equipped_item";

    @Mock
    private IntegerEncryptor integerEncryptor;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private SlotConverter underTest;


    @Test
    public void testConvertDomainShouldEncryptAndConvert() throws JsonProcessingException {
        //GIVEN
        EquippedSlot slot = EquippedSlot.builder()
            .slotId(EQUIPPED_SLOT_ID)
            .shipId(SHIP_ID)
            .frontSlot(EQUIPPED_SLOT)
            .leftSlot(EQUIPPED_SLOT)
            .rightSlot(EQUIPPED_SLOT)
            .backSlot(EQUIPPED_SLOT)
            .frontEquipped(Arrays.asList(EQUIPPED_ITEM))
            .leftEquipped(Arrays.asList(EQUIPPED_ITEM))
            .rightEquipped(Arrays.asList(EQUIPPED_ITEM))
            .backEquipped(Arrays.asList(EQUIPPED_ITEM))
            .build();

        when(integerEncryptor.encryptEntity(anyInt(), eq(EQUIPPED_SLOT_ID))).thenReturn(ENCRYPTED_SLOT);
        when(objectMapper.writeValueAsString(any(List.class))).thenReturn(EQUIPPED_ITEMS);
        when(stringEncryptor.encryptEntity(anyString(), eq(EQUIPPED_SLOT_ID))).thenReturn(ENCRYPTED_EQUIPPED_ITEMS);
        //WHEN
        SlotEntity result = underTest.convertDomain(slot);
        //THEN
        assertThat(result.getSlotId()).isEqualTo(EQUIPPED_SLOT_ID);
        assertThat(result.getShipId()).isEqualTo(SHIP_ID);
        assertThat(result.getFrontEquipped()).isEqualTo(ENCRYPTED_EQUIPPED_ITEMS);
        assertThat(result.getLeftEquipped()).isEqualTo(ENCRYPTED_EQUIPPED_ITEMS);
        assertThat(result.getRightEquipped()).isEqualTo(ENCRYPTED_EQUIPPED_ITEMS);
        assertThat(result.getBackEquipped()).isEqualTo(ENCRYPTED_EQUIPPED_ITEMS);
        assertThat(result.getFrontSlot()).isEqualTo(ENCRYPTED_SLOT);
        assertThat(result.getLeftSlot()).isEqualTo(ENCRYPTED_SLOT);
        assertThat(result.getRightSlot()).isEqualTo(ENCRYPTED_SLOT);
        assertThat(result.getBackSlot()).isEqualTo(ENCRYPTED_SLOT);
    }

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        SlotEntity entity = null;
        //WHEN
        EquippedSlot result = underTest.convertEntity(entity);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert() throws IOException {
        //GIVEN
        SlotEntity entity = SlotEntity.builder()
            .slotId(EQUIPPED_SLOT_ID)
            .shipId(SHIP_ID)
            .frontSlot(ENCRYPTED_SLOT)
            .leftSlot(ENCRYPTED_SLOT)
            .rightSlot(ENCRYPTED_SLOT)
            .backSlot(ENCRYPTED_SLOT)
            .frontEquipped(ENCRYPTED_EQUIPPED_ITEMS)
            .leftEquipped(ENCRYPTED_EQUIPPED_ITEMS)
            .rightEquipped(ENCRYPTED_EQUIPPED_ITEMS)
            .backEquipped(ENCRYPTED_EQUIPPED_ITEMS)
            .build();
        when(integerEncryptor.decryptEntity(anyString(), eq(EQUIPPED_SLOT_ID))).thenReturn(EQUIPPED_SLOT);
        when(stringEncryptor.decryptEntity(anyString(), eq(EQUIPPED_SLOT_ID))).thenReturn(EQUIPPED_ITEMS);
        String[] dataArray = new String[]{EQUIPPED_ITEM};
        when(objectMapper.readValue(EQUIPPED_ITEMS, String[].class)).thenReturn(dataArray);
        //WHEN
        EquippedSlot result = underTest.convertEntity(entity);
        //THEN

        assertThat(result.getSlotId()).isEqualTo(EQUIPPED_SLOT_ID);
        assertThat(result.getShipId()).isEqualTo(SHIP_ID);
        assertThat(result.getFrontSlot()).isEqualTo(EQUIPPED_SLOT);
        assertThat(result.getLeftSlot()).isEqualTo(EQUIPPED_SLOT);
        assertThat(result.getRightSlot()).isEqualTo(EQUIPPED_SLOT);
        assertThat(result.getBackSlot()).isEqualTo(EQUIPPED_SLOT);
        assertThat(result.getFrontEquipped()).hasSize(1).containsOnly(EQUIPPED_ITEM);
        assertThat(result.getLeftEquipped()).hasSize(1).containsOnly(EQUIPPED_ITEM);
        assertThat(result.getRightEquipped()).hasSize(1).containsOnly(EQUIPPED_ITEM);
        assertThat(result.getBackEquipped()).hasSize(1).containsOnly(EQUIPPED_ITEM);
    }
}
