package skyxplore.domain.materials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.encryption.StringEncryptor;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class MaterialsConverterTest {
    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private MaterialsConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull(){
        //WHEN
        Materials result = underTest.convertEntity(null, null);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert() throws IOException {
        //GIVEN
        when(stringEncryptor.decryptEntity(MATERIAL_ENCRYPTED_ENTITY, FACTORY_ID_1)).thenReturn(MATERIAL_ENTITY);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(MATERIAL_ID, MATERIAL_AMOUNT);
        when(objectMapper.readValue(MATERIAL_ENTITY, HashMap.class)).thenReturn(map);
        //WHEN
        Materials result = underTest.convertEntity(MATERIAL_ENCRYPTED_ENTITY, FACTORY_ID_1);
        //THEN
        verify(stringEncryptor).decryptEntity(MATERIAL_ENCRYPTED_ENTITY, FACTORY_ID_1);
        verify(objectMapper).readValue(MATERIAL_ENTITY, HashMap.class);
        assertEquals(map, result);
    }

    @Test
    public void testConvertDomainShouldConvertAndEncrypt() throws JsonProcessingException {
        //GIVEN
        Materials materials = new Materials();

        when(objectMapper.writeValueAsString(materials)).thenReturn(MATERIAL_ENTITY);
        when(stringEncryptor.encryptEntity(MATERIAL_ENTITY, FACTORY_ID_1)).thenReturn(MATERIAL_ENCRYPTED_ENTITY);
        //WHEN
        String result = underTest.convertDomain(materials, FACTORY_ID_1);
        //THEN
        verify(objectMapper).writeValueAsString(materials);
        verify(stringEncryptor).encryptEntity(MATERIAL_ENTITY, FACTORY_ID_1);
        assertEquals(MATERIAL_ENCRYPTED_ENTITY, result);
    }
}