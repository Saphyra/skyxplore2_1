package skyxplore.encryption.base;

import org.apache.commons.net.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class FinalEncryptor{
    private static final int SIZE = 16;
    private static final String ALGORITHM = "AES";

    private static final Base64 BASE_64 = new Base64();

    private final Key key;
    private final Cipher cipher;


    public FinalEncryptor(String password) throws NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] key = createKey(password);
        this.key = new SecretKeySpec(key, ALGORITHM);
        cipher = Cipher.getInstance(ALGORITHM);
    }

    private byte[] createKey(String password) {
        if (password.length() < SIZE) {
            int missingLength = SIZE - password.length();
            for (int i = 0; i < missingLength; i++) {
                password += " ";
            }
        }
        return password.substring(0, SIZE).getBytes(StandardCharsets.UTF_8);
    }

    public String encrypt(String text) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        byte[] base64 = BASE_64.encode(encrypted);
        return new String(base64, StandardCharsets.UTF_8);
    }

    public String decrypt(String text) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] base64 = BASE_64.decode(text.getBytes(StandardCharsets.UTF_8));
        byte[] decrypted = cipher.doFinal(base64);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
