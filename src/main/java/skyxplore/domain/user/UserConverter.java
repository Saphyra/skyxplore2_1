package skyxplore.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

@Component
@RequiredArgsConstructor
public class UserConverter extends ConverterBase<UserEntity, SkyXpUser> {

    @Override
    public SkyXpUser convertEntity(UserEntity entity){
        if(entity == null){
            return null;
        }
        SkyXpUser user = new SkyXpUser();
            user.setUserId(entity.getUserId());
            user.setUsername(entity.getUsername());
            user.setPassword(entity.getPassword());
            user.setEmail(entity.getEmail());
            user.setRoles(entity.getRoles());
        return user;
    }

    @Override
    public UserEntity convertDomain(SkyXpUser domain){
        UserEntity entity = new UserEntity();
        entity.setUserId(domain.getUserId());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword());
        entity.setEmail(domain.getEmail());
        entity.setRoles(domain.getRoles());

        return entity;
    }
}
