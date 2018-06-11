package skyxplore.dataaccess.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.character.CharacterDao;
import skyxplore.dataaccess.user.converter.RoleConverter;
import skyxplore.dataaccess.user.converter.UserConverter;
import skyxplore.dataaccess.user.entity.UserEntity;
import skyxplore.dataaccess.user.repository.UserRepository;
import skyxplore.exception.UserNotFoundException;
import skyxplore.service.domain.SkyXpUser;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDao {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleConverter roleConverter;
    private final CharacterDao characterDao;

    public void delete(Long userId){
        characterDao.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    public SkyXpUser findUserByEmail(String email){
        return userConverter.convertEntity(userRepository.findByEmail(email));
    }

    public SkyXpUser findById(Long userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isPresent()){
            return userConverter.convertEntity(user.get());
        }
        throw new UserNotFoundException("User not found with id" + userId);
    }

    public SkyXpUser findUserByUserName(String userName){
        return userConverter.convertEntity(userRepository.findByUsername(userName));
    }

    public SkyXpUser registrateUser(SkyXpUser user){
        UserEntity registrated = userRepository.save(userConverter.convertDomain(user));
        return userConverter.convertEntity(registrated);
    }

    @Transactional
    public void update(SkyXpUser user){
        Optional<UserEntity> actual = userRepository.findById(user.getUserId());
        if(actual.isPresent()){
            UserEntity entity = actual.get();
            entity.setUserId(user.getUserId());
            entity.setUsername(user.getUsername());
            entity.setEmail(user.getEmail());
            entity.setPassword(user.getPassword());
            entity.setRoles(roleConverter.convertDomain(user.getRoles()));
        }else{
            throw new UserNotFoundException("User not found with id" + user.getUserId());
        }
    }
}
