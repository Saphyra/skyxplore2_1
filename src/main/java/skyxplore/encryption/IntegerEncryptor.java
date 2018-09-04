package skyxplore.encryption;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class IntegerEncryptor{
    private final StringEncryptor stringEncryptor;

    public String encrypt(Integer entity, String key) {
        return stringEncryptor.encrypt(entity.toString(), key);
    }

    public Integer decrypt(String entity, String key) {
        return Integer.valueOf(stringEncryptor.decrypt(entity, key));
    }
}
