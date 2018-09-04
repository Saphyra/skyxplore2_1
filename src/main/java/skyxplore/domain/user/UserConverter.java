package skyxplore.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.encryption.UserEncryptor;
import skyxplore.domain.ConverterBase;

@Component
@RequiredArgsConstructor
//TODO unit test
public class UserConverter extends ConverterBase<UserEntity, SkyXpUser> {
    private final UserEncryptor userEncryptor;

    @Override
    public SkyXpUser convertEntity(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserEntity decrypted = userEncryptor.decryptEntity(entity, entity.getUserId());

        SkyXpUser user = new SkyXpUser();
        user.setUserId(decrypted.getUserId());
        user.setEmail(decrypted.getEmail());
        user.setRoles(decrypted.getRoles());
        return user;
    }

    @Override
    public UserEntity convertDomain(SkyXpUser domain) {
        if(domain == null){
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setUserId(domain.getUserId());
        entity.setEmail(domain.getEmail());
        entity.setRoles(domain.getRoles());

        return userEncryptor.encryptEntity(entity, domain.getUserId());
    }
}
