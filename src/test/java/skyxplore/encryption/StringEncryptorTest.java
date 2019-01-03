package skyxplore.encryption;

import com.github.saphyra.encryption.impl.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

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
        String decrypted = underTest.decryptEntity(encrypted, KEY);
        assertEquals(TEST_ENTITY, decrypted);
    }
}