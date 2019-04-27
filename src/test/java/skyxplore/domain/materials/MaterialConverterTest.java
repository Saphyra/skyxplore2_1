package skyxplore.domain.materials;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CONVERTER_ENTITY;
import static skyxplore.testutil.TestUtils.CONVERTER_INT_VALUE;
import static skyxplore.testutil.TestUtils.CONVERTER_KEY;

@RunWith(MockitoJUnitRunner.class)
public class MaterialConverterTest {
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MaterialsConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        String entity = null;
        //WHEN
        Materials result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvert() throws IOException {
        //GIVEN
        HashMap<String, Integer> map = new HashMap<>();
        map.put(CONVERTER_KEY, CONVERTER_INT_VALUE);
        when(objectMapper.readValue(CONVERTER_ENTITY, HashMap.class)).thenReturn(map);
        //WHEN
        Materials result = underTest.convertEntity(CONVERTER_ENTITY);
        //THEN
        assertTrue(result.containsKey(CONVERTER_KEY));
        assertTrue(result.containsValue(CONVERTER_INT_VALUE));
    }

    @Test
    public void testConvertDomainShouldConvert() throws IOException {
        //GIVEN
        Materials materials = new Materials();
        when(objectMapper.writeValueAsString(materials)).thenReturn(CONVERTER_ENTITY);
        //WHEN
        String result = underTest.convertDomain(materials);
        //THEN
        assertEquals(CONVERTER_ENTITY, result);
    }
}
