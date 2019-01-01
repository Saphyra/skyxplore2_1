package skyxplore.domain.slot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.encryption.IntegerEncryptor;
import skyxplore.encryption.StringEncryptor;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_DATA_ITEM_STRING;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_ENCRYPTED_SLOT;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_ENCRYPTED_SLOT_ITEM;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_FRONT_SLOT;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_ID;
import static skyxplore.testutil.TestUtils.createEquippedSlot;
import static skyxplore.testutil.TestUtils.createSlotEntity;

@RunWith(MockitoJUnitRunner.class)
public class SlotConverterTest {

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
        EquippedSlot slot = createEquippedSlot();

        when(integerEncryptor.encryptEntity(anyInt(), eq(EQUIPPED_SLOT_ID))).thenReturn(EQUIPPED_SLOT_ENCRYPTED_SLOT);
        when(objectMapper.writeValueAsString(any(ArrayList.class))).thenReturn(EQUIPPED_SLOT_DATA_ITEM_STRING);
        when(stringEncryptor.encryptEntity(anyString(), eq(EQUIPPED_SLOT_ID))).thenReturn(EQUIPPED_SLOT_ENCRYPTED_SLOT_ITEM);
        //WHEN
        SlotEntity result = underTest.convertDomain(slot);
        //THEN
        verify(integerEncryptor, times(4)).encryptEntity(anyInt(), eq(EQUIPPED_SLOT_ID));
        verify(stringEncryptor, times(4)).encryptEntity(anyString(), eq(EQUIPPED_SLOT_ID));
        verify(objectMapper, times(4)).writeValueAsString(any(ArrayList.class));
        assertEquals(EQUIPPED_SLOT_ID, result.getSlotId());
        assertEquals(EQUIPPED_SHIP_ID, result.getShipId());
        assertEquals(EQUIPPED_SLOT_ENCRYPTED_SLOT, result.getFrontSlot());
        assertEquals(EQUIPPED_SLOT_ENCRYPTED_SLOT, result.getLeftSlot());
        assertEquals(EQUIPPED_SLOT_ENCRYPTED_SLOT, result.getRightSlot());
        assertEquals(EQUIPPED_SLOT_ENCRYPTED_SLOT, result.getBackSlot());
        assertEquals(EQUIPPED_SLOT_ENCRYPTED_SLOT_ITEM, result.getFrontEquipped());
        assertEquals(EQUIPPED_SLOT_ENCRYPTED_SLOT_ITEM, result.getLeftEquipped());
        assertEquals(EQUIPPED_SLOT_ENCRYPTED_SLOT_ITEM, result.getRightEquipped());
        assertEquals(EQUIPPED_SLOT_ENCRYPTED_SLOT_ITEM, result.getBackEquipped());
    }

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        SlotEntity entity = null;
        //WHEN
        EquippedSlot result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert() throws IOException {
        //GIVEN
        SlotEntity entity = createSlotEntity();
        when(integerEncryptor.decryptEntity(anyString(), eq(EQUIPPED_SLOT_ID))).thenReturn(EQUIPPED_SLOT_FRONT_SLOT);
        when(stringEncryptor.decryptEntity(anyString(), eq(EQUIPPED_SLOT_ID))).thenReturn(EQUIPPED_SLOT_DATA_ITEM_STRING);
        ArrayList<String> list = new ArrayList<>();
        list.add(DATA_ELEMENT);
        when(objectMapper.readValue(EQUIPPED_SLOT_DATA_ITEM_STRING, ArrayList.class)).thenReturn(list);
        //WHEN
        EquippedSlot result = underTest.convertEntity(entity);
        //THEN
        verify(integerEncryptor, times(4)).decryptEntity(anyString(), eq(EQUIPPED_SLOT_ID));
        verify(stringEncryptor, times(4)).decryptEntity(anyString(), eq(EQUIPPED_SLOT_ID));
        verify(objectMapper, times(4)).readValue(EQUIPPED_SLOT_DATA_ITEM_STRING, ArrayList.class);
        assertEquals(EQUIPPED_SLOT_ID, result.getSlotId());
        assertEquals(EQUIPPED_SHIP_ID, result.getShipId());
        assertEquals(EQUIPPED_SLOT_FRONT_SLOT, result.getFrontSlot());
        assertEquals(EQUIPPED_SLOT_FRONT_SLOT, result.getLeftSlot());
        assertEquals(EQUIPPED_SLOT_FRONT_SLOT, result.getRightSlot());
        assertEquals(EQUIPPED_SLOT_FRONT_SLOT, result.getBackSlot());
        assertEquals(list, result.getFrontEquipped());
        assertEquals(list, result.getLeftEquipped());
        assertEquals(list, result.getRightEquipped());
        assertEquals(list, result.getBackEquipped());
    }
}
