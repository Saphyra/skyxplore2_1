package skyxplore.encryption.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
@Slf4j
public class StringEncryptor implements Encryptor<String> {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE_BYTE = 32;

    @Override
    public String encryptEntity(String entity, String key) {
        return crypt(entity, key, Cipher.ENCRYPT_MODE);
    }

    @Override
    public String decryptEntity(String entity, String key) {
        return crypt(entity, key, Cipher.DECRYPT_MODE);
    }

    private String crypt(String entity, String key, int mode) {
        String result = null;
        try {
            byte[] bytes = getKeyBytes(key);
            SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(mode, secretKeySpec);
            result = new String(cipher.doFinal(entity.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private byte[] getKeyBytes(String s) throws UnsupportedEncodingException {
        if (s.length() < KEY_SIZE_BYTE) {
            int missingLength = KEY_SIZE_BYTE - s.length();
            for (int i = 0; i < missingLength; i++) {
                s += " ";
            }
        }
        return s.substring(0, KEY_SIZE_BYTE).getBytes("UTF-8");
    }
}
