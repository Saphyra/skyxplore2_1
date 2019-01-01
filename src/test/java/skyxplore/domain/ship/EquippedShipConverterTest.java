package skyxplore.domain.ship;

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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class EquippedShipConverterTest {
    @Mock
    private IntegerEncryptor integerEncryptor;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private EquippedShipConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull() {
        //GIVEN
        EquippedShipEntity entity = null;
        //WHEN
        EquippedShip result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert() throws IOException {
        //GIVEN
        EquippedShipEntity equippedShipEntity = createEquippedShipEntity();

        when(stringEncryptor.decryptEntity(EQUIPPED_SHIP_ENCRYPTED_SHIP_TYPE, EQUIPPED_SHIP_ID)).thenReturn(EQUIPPED_SHIP_TYPE);
        when(integerEncryptor.decryptEntity(EQUIPPED_SHIP_ENCRYPTED_COREHULL, EQUIPPED_SHIP_ID)).thenReturn(DATA_SHIP_COREHULL);
        when(integerEncryptor.decryptEntity(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_SLOT, EQUIPPED_SHIP_ID)).thenReturn(DATA_SHIP_CONNECTOR_SLOT);
        when(stringEncryptor.decryptEntity(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_EQUIPPED, EQUIPPED_SHIP_ID)).thenReturn(EQUIPPED_SHIP_CONNECTOR_EQUIPPED);
        ArrayList<String> connectors = new ArrayList<>();
        connectors.add(EQUIPPED_SHIP_CONNECTOR_EQUIPPED);
        when(objectMapper.readValue(EQUIPPED_SHIP_CONNECTOR_EQUIPPED, ArrayList.class)).thenReturn(connectors);
        //WHEN
        EquippedShip result = underTest.convertEntity(equippedShipEntity);
        //THEN
        verify(stringEncryptor).decryptEntity(EQUIPPED_SHIP_ENCRYPTED_SHIP_TYPE, EQUIPPED_SHIP_ID);
        verify(integerEncryptor).decryptEntity(EQUIPPED_SHIP_ENCRYPTED_COREHULL, EQUIPPED_SHIP_ID);
        verify(integerEncryptor).decryptEntity(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_SLOT, EQUIPPED_SHIP_ID);
        verify(stringEncryptor).decryptEntity(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_EQUIPPED, EQUIPPED_SHIP_ID);
        verify(objectMapper).readValue(EQUIPPED_SHIP_CONNECTOR_EQUIPPED, ArrayList.class);
        assertEquals(EQUIPPED_SHIP_ID, result.getShipId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(EQUIPPED_SHIP_TYPE, result.getShipType());
        assertEquals(DATA_SHIP_COREHULL, result.getCoreHull());
        assertEquals(DATA_SHIP_CONNECTOR_SLOT, result.getConnectorSlot());
        assertTrue(result.getConnectorEquipped().contains(EQUIPPED_SHIP_CONNECTOR_EQUIPPED));
        assertEquals(DEFENSE_SLOT_ID, result.getDefenseSlotId());
        assertEquals(WEAPON_SLOT_ID, result.getWeaponSlotId());
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert() throws JsonProcessingException {
        //GIVEN
        EquippedShip equippedShip = createEquippedShip();

        when(stringEncryptor.encryptEntity(EQUIPPED_SHIP_TYPE, EQUIPPED_SHIP_ID)).thenReturn(EQUIPPED_SHIP_ENCRYPTED_SHIP_TYPE);
        when(integerEncryptor.encryptEntity(DATA_SHIP_COREHULL, EQUIPPED_SHIP_ID)).thenReturn(EQUIPPED_SHIP_ENCRYPTED_COREHULL);
        when(integerEncryptor.encryptEntity(DATA_SHIP_CONNECTOR_SLOT, EQUIPPED_SHIP_ID)).thenReturn(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_SLOT);
        when(objectMapper.writeValueAsString(any(ArrayList.class))).thenReturn(EQUIPPED_SHIP_CONNECTOR_EQUIPPED);
        when(stringEncryptor.encryptEntity(EQUIPPED_SHIP_CONNECTOR_EQUIPPED, EQUIPPED_SHIP_ID)).thenReturn(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_EQUIPPED);
        //WHEN
        EquippedShipEntity result = underTest.convertDomain(equippedShip);
        //THEN
        verify(stringEncryptor).encryptEntity(EQUIPPED_SHIP_TYPE, EQUIPPED_SHIP_ID);
        verify(integerEncryptor).encryptEntity(DATA_SHIP_COREHULL, EQUIPPED_SHIP_ID);
        verify(integerEncryptor).encryptEntity(DATA_SHIP_CONNECTOR_SLOT, EQUIPPED_SHIP_ID);
        verify(stringEncryptor).encryptEntity(EQUIPPED_SHIP_CONNECTOR_EQUIPPED, EQUIPPED_SHIP_ID);
        verify(objectMapper).writeValueAsString(any(ArrayList.class));
        assertEquals(EQUIPPED_SHIP_ID, result.getShipId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(EQUIPPED_SHIP_ENCRYPTED_SHIP_TYPE, result.getShipType());
        assertEquals(EQUIPPED_SHIP_ENCRYPTED_COREHULL, result.getCoreHull());
        assertEquals(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_SLOT, result.getConnectorSlot());
        assertEquals(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_EQUIPPED, result.getConnectorEquipped());
        assertEquals(DEFENSE_SLOT_ID, result.getDefenseSlotId());
        assertEquals(WEAPON_SLOT_ID, result.getWeaponSlotId());
    }
}
