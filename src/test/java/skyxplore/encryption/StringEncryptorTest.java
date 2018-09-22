package skyxplore.encryption;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StringEncryptorTest {
    private static final String TEST_ENTITY = "test_entity";
    private static final String KEY = "key";

    @InjectMocks
    private StringEncryptor underTest;

    @Test
    public void testEncryptEntityShouldReturnNullWhenNull(){
        assertNull(underTest.encryptEntity(null, KEY));
    }

    @Test
    public void testDecryptEntityShouldReturnNullWhenNull(){
        assertNull(underTest.decryptEntity(null, KEY));
    }

    @Test
    public void testShouldEncryptAndDecrypt(){
        String encrypted = underTest.encryptEntity(TEST_ENTITY, KEY);
        assertNotEquals(TEST_ENTITY, encrypted);
        String decrypted = underTest.decrypt(encrypted, KEY);
        assertEquals(TEST_ENTITY, decrypted);
    }
}