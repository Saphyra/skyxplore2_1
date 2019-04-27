package skyxplore.domain.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ENCRYPTED_EQUIPMENTS;
import static skyxplore.testutil.TestUtils.CHARACTER_ENCRYPTED_MONEY;
import static skyxplore.testutil.TestUtils.CHARACTER_EQUIPMENT;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_MONEY;
import static skyxplore.testutil.TestUtils.CHARACTER_NAME;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createCharacterEntity;

@RunWith(MockitoJUnitRunner.class)
public class CharacterConverterTest {
    @Mock
    private IntegerEncryptor integerEncryptor;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private CharacterConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        CharacterEntity entity = null;
        //WHEN
        SkyXpCharacter result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvertAndDecrypt() throws IOException {
        //GIVEN
        CharacterEntity entity = createCharacterEntity();
        when(integerEncryptor.decryptEntity(CHARACTER_ENCRYPTED_MONEY, CHARACTER_ID_1)).thenReturn(CHARACTER_MONEY);

        when(stringEncryptor.decryptEntity(CHARACTER_ENCRYPTED_EQUIPMENTS, CHARACTER_ID_1)).thenReturn(CHARACTER_EQUIPMENT);

        String[] equipmentArray = new String[]{CHARACTER_EQUIPMENT};
        List<String> equipmentList = Arrays.asList(equipmentArray);
        when(objectMapper.readValue(CHARACTER_EQUIPMENT, String[].class)).thenReturn(equipmentArray);
        //WHEN
        SkyXpCharacter result = underTest.convertEntity(entity);
        //THEN
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(CHARACTER_NAME, result.getCharacterName());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(CHARACTER_MONEY, result.getMoney());
        assertEquals(equipmentList, result.getEquipments());
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert() throws JsonProcessingException {
        //GIVEN
        SkyXpCharacter character = createCharacter();

        when(integerEncryptor.encryptEntity(CHARACTER_MONEY, CHARACTER_ID_1)).thenReturn(CHARACTER_ENCRYPTED_MONEY);

        when(objectMapper.writeValueAsString(character.getEquipments())).thenReturn(CHARACTER_EQUIPMENT);
        when(stringEncryptor.encryptEntity(CHARACTER_EQUIPMENT, CHARACTER_ID_1)).thenReturn(CHARACTER_ENCRYPTED_EQUIPMENTS);
        //WHEN
        CharacterEntity result = underTest.convertDomain(character);
        //THEN
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(CHARACTER_NAME, result.getCharacterName());
        assertEquals(CHARACTER_ENCRYPTED_MONEY, result.getMoney());
        assertEquals(CHARACTER_ENCRYPTED_EQUIPMENTS, result.getEquipments());
    }
}