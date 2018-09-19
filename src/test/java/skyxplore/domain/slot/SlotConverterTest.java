package skyxplore.domain.slot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_DATA_ITEM_STRING;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_ENCRYPTED_SLOT;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_ENCRYPTED_SLOT_ITEM;
import static skyxplore.testutil.TestUtils.EQUIPPED_SLOT_ID;
import static skyxplore.testutil.TestUtils.createEquippedSlot;
import static skyxplore.testutil.TestUtils.createSlotEntity;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import skyxplore.encryption.IntegerEncryptor;
import skyxplore.encryption.StringEncryptor;

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

    @Test(expected = IllegalArgumentException.class)
    public void testConvertDomainShouldThrowExceptionWhenNull(){
        //GIVEN
        EquippedSlot slot = null;
        //WHEN
        underTest.convertDomain(slot);
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert() throws JsonProcessingException {
        //GIVEN
        EquippedSlot slot = createEquippedSlot();

        when(integerEncryptor.encrypt(anyInt(), eq(EQUIPPED_SLOT_ID))).thenReturn(EQUIPPED_SLOT_ENCRYPTED_SLOT);
        when(objectMapper.writeValueAsString(any(ArrayList.class))).thenReturn(EQUIPPED_SLOT_DATA_ITEM_STRING);
        when(stringEncryptor.encryptEntity(anyString(), eq(EQUIPPED_SLOT_ID))).thenReturn(EQUIPPED_SLOT_ENCRYPTED_SLOT_ITEM);
        //WHEN
        SlotEntity result = underTest.convertDomain(slot);
        //THEN
        verify(integerEncryptor, times(4)).encrypt(anyInt(), eq(EQUIPPED_SLOT_ID));
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
    public void testConvertEntityShouldReturnNullWhenNull(){
        //GIVEN
        SlotEntity entity = null;
        //WHEN
        EquippedSlot result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert(){
        //GIVEN
        SlotEntity entity = createSlotEntity();
        //WHEN
        EquippedSlot result = underTest.convertEntity(entity);
        //THEN
    }
}
