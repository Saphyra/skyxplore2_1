package skyxplore.encryption;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.user.UserEntity;
import skyxplore.encryption.base.Encryptor;
import skyxplore.encryption.base.StringEncryptor;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserEncryptor implements Encryptor<UserEntity> {
    private final StringEncryptor stringEncryptor;

    @Override
    public UserEntity encryptEntity(UserEntity entity, String key) {
        return entity;
        /*return UserEntity.builder()
            .userId(entity.getUserId())
            .username(stringEncryptor.encryptEntity(entity.getUsername(), key))
            .email(stringEncryptor.encryptEntity(entity.getEmail(), key))
            .password(stringEncryptor.encryptEntity(entity.getPassword(), key))
            .roles(entity.getRoles())
            .build();*/
    }

    @Override
    public UserEntity decryptEntity(UserEntity entity, String key) {
        return entity;
        /*return UserEntity.builder()
            .userId(entity.getUserId())
            .username(stringEncryptor.decryptEntity(entity.getUsername(), key))
            .email(stringEncryptor.decryptEntity(entity.getEmail(), key))
            .password(stringEncryptor.decryptEntity(entity.getPassword(), key))
            .roles(entity.getRoles())
            .build();*/
    }
}
