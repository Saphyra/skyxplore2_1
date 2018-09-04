package skyxplore.encryption;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.encryption.base.DefaultEncryptor;
import skyxplore.encryption.base.Encryptor;

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

    @Override
    public String encryptEntity(String entity, String key) {
        DefaultEncryptor encryption = new DefaultEncryptor(key);
        return encryption.encrypt(entity);
    }

    @Override
    public String decryptEntity(String entity, String key) {
        DefaultEncryptor decryption = new DefaultEncryptor(key);
        return decryption.decrypt(entity);
    }
}
