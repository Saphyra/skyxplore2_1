package skyxplore.dataaccess.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.user.converter.UserConverter;
import skyxplore.dataaccess.user.entity.UserEntity;
import skyxplore.dataaccess.user.repository.UserRepository;
import skyxplore.pages.index.domain.SkyXpUser;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDao {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public SkyXpUser findUserByEmail(String email){
        return userConverter.convertEntity(userRepository.findByEmail(email));
    }

    public SkyXpUser findUserByUserName(String userName){
        return userConverter.convertEntity(userRepository.findByUsername(userName));
    }

    public SkyXpUser registrateUser(SkyXpUser user){
        UserEntity registrated = userRepository.save(userConverter.convertDomain(user));
        return userConverter.convertEntity(registrated);
    }
}
