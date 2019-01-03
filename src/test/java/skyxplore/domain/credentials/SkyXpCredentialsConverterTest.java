package skyxplore.domain.credentials;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static skyxplore.testutil.TestUtils.CREDENTIALS_HASHED_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NAME;
import static skyxplore.testutil.TestUtils.createCredentials;
import static skyxplore.testutil.TestUtils.createCredentialsEntity;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpCredentialsConverterTest {
    @InjectMocks
    private CredentialsConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull(){
        //GIVEN
        CredentialsEntity entity = null;
        //WHEN
        SkyXpCredentials result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void tstConvertEntityShouldConvert(){
        //GIVEN
        CredentialsEntity entity = createCredentialsEntity();
        //WHEN
        SkyXpCredentials result = underTest.convertEntity(entity);
        //THEN
        assertEquals(USER_ID, result.getUserId());
        assertEquals(USER_NAME, result.getUserName());
        assertEquals(CREDENTIALS_HASHED_PASSWORD, result.getPassword());
    }

    @Test
    public void testConvertDomainShouldConvert(){
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();
        //WHEN
        CredentialsEntity result = underTest.convertDomain(skyXpCredentials);
        //THEN
        assertEquals(USER_ID, result.getUserId());
        assertEquals(USER_NAME, result.getUserName());
        assertEquals(CREDENTIALS_HASHED_PASSWORD, result.getPassword());
    }
}
