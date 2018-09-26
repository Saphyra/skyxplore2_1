package skyxplore.encryption.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static skyxplore.testutil.TestUtils.USER_FAKE_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_PASSWORD;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordServiceTest {
    @InjectMocks
    private PasswordService underTest;

    @Test
    public void testShouldHashAndReturnTrueWhenSamePassword(){
        //GIVEN
        String hashed = underTest.hashPassword(USER_PASSWORD);
        //WHEN
        boolean result = underTest.authenticate(USER_PASSWORD, hashed);
        //THEN
        assertTrue(result);
    }

    @Test
    public void testShouldHashAndReturnFalseWhenDifferentPassword(){
        //GIVEN
        String hashed = underTest.hashPassword(USER_PASSWORD);
        //WHEN
        boolean result = underTest.authenticate(USER_FAKE_PASSWORD, hashed);
        //THEN
        assertFalse(result);
    }
}