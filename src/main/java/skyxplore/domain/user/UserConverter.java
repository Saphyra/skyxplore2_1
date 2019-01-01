package skyxplore.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;

@Component
@RequiredArgsConstructor
public class UserConverter extends ConverterBase<UserEntity, SkyXpUser> {

    @Override
    public SkyXpUser processEntityConversion(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        SkyXpUser user = new SkyXpUser();
        user.setUserId(entity.getUserId());
        user.setEmail(entity.getEmail());
        user.setRoles(entity.getRoles());
        return user;
    }

    @Override
    public UserEntity processDomainConversion(SkyXpUser domain) {
        if(domain == null){
            throw new IllegalArgumentException("domain must not be null.");
        }

        UserEntity entity = new UserEntity();
        entity.setUserId(domain.getUserId());
        entity.setEmail(domain.getEmail());
        entity.setRoles(domain.getRoles());

        return entity;
    }
}
