package skyxplore.domain.user;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {
    @InjectMocks
    private UserConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull(){
        //GIVEN
        UserEntity entity = null;
        //WHEN
        SkyXpUser result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvert(){
        //GIVEN
        UserEntity entity = createUserEntity();
        //WHEN
        SkyXpUser result = underTest.convertEntity(entity);
        //THEN
        assertEquals(USER_ID, result.getUserId());
        assertEquals(USER_EMAIL, result.getEmail());
        assertEquals(1, result.getRoles().size());
        assertTrue(result.getRoles().contains(Role.USER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertDomainShouldThrowExceptionWhenNull(){
        //GIVEN
        SkyXpUser user = null;
        //WHEN
        underTest.convertDomain(user);
    }

    @Test
    public void testConvertDomainShouldConvert(){
        //GIVEN
        SkyXpUser user = createUser();
        //WHEN
        UserEntity result = underTest.convertDomain(user);
        //THEN
        assertEquals(USER_ID, result.getUserId());
        assertEquals(USER_EMAIL, result.getEmail());
        assertEquals(1, result.getRoles().size());
        assertTrue(result.getRoles().contains(Role.USER));
    }
}