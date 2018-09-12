package skyxplore.domain.ship;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.encryption.IntegerEncryptor;
import skyxplore.encryption.StringEncryptor;

import static org.junit.Assert.assertNull;
import static skyxplore.testutil.TestUtils.createEquippedShipEntity;

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
    public void testConvertEntityShouldReturnNull(){
        //GIVEN
        EquippedShipEntity entity = null;
        //WHEN
        EquippedShip result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    //TODO finish
    public void testConvertEntityShouldDecryptAndConvert(){
        //GIVEN
        EquippedShipEntity equippedShipEntity = createEquippedShipEntity();
        //WHEN
        EquippedShip result = underTest.convertEntity(equippedShipEntity);
        //THEN
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertDomainShouldThrowExceptionWhenNull(){
        //GIVEN
        EquippedShip equippedShip = null;
        //WHEN
        underTest.convertDomain(equippedShip);
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert(){

    }
}
