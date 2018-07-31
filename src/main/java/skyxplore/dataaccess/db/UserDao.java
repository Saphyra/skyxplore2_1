package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.UserRepository;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.domain.user.UserConverter;
import skyxplore.domain.user.UserEntity;
import skyxplore.exception.UserNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDao {
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    public void delete(String userId){
        log.info("Deleting user {}", userId);
        userRepository.deleteById(userId);
    }

    public SkyXpUser findUserByEmail(String email){
        return userConverter.convertEntity(userRepository.findByEmail(email));
    }

    public SkyXpUser findById(String userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        return user.map(userConverter::convertEntity).orElse(null);
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
            entity.setRoles(user.getRoles());
        }else{
            throw new UserNotFoundException("User not found with id" + user.getUserId());
        }
    }
}
