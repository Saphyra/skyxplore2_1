package skyxplore.dataaccess.user.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.user.entity.UserEntity;
import skyxplore.home.domain.SkyXpUser;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final RoleConverter roleConverter;

    public SkyXpUser convertEntity(UserEntity entity){
        if(entity == null){
            return null;
        }
        SkyXpUser user = new SkyXpUser();
            user.setUserId(entity.getUserId());
            user.setUsername(entity.getUsername());
            user.setPassword(entity.getPassword());
            user.setEmail(entity.getEmail());
            user.setRoles(roleConverter.convertEntity(entity.getRoles()));
        return user;
    }

    public UserEntity convertDomain(SkyXpUser domain){
        UserEntity entity = new UserEntity();
        entity.setUserId(domain.getUserId());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword());
        entity.setEmail(domain.getEmail());
        entity.setRoles(roleConverter.convertDomain(domain.getRoles()));

        return entity;
    }
}
