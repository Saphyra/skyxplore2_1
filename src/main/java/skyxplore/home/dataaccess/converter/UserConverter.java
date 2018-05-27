package skyxplore.home.dataaccess.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.home.dataaccess.user.entity.UserEntity;
import skyxplore.home.service.domain.SkyXpUser;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final RoleConverter roleConverter;

    public SkyXpUser convert(UserEntity entity){
        if(entity == null){
            return null;
        }
        SkyXpUser user = new SkyXpUser();
            user.setUsername(entity.getUsername());
            user.setPassword(entity.getPassword());
            user.setEmail(entity.getEmail());
            user.setRoles(roleConverter.convertEntity(entity.getRoles()));
        return user;
    }
}
