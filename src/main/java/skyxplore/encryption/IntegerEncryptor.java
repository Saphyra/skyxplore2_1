package skyxplore.encryption;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
//TODO implement
public class IntegerEncryptor{
    private final StringEncryptor stringEncryptor;

    protected String encrypt(Integer entity, String key) {
        return null;
    }

    protected Integer decrypt(String entity, String key) {
        return null;
    }
}
