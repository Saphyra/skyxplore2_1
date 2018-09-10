package skyxplore.dataaccess.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.UserRepository;
import skyxplore.domain.Converter;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.domain.user.UserEntity;
import skyxplore.exception.UserNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Slf4j
public class UserDao extends AbstractDao<UserEntity, SkyXpUser, String, UserRepository>{
    private final CredentialsDao credentialsDao;

    public UserDao(
        Converter<UserEntity, SkyXpUser> converter,
        UserRepository repository,
        CredentialsDao credentialsDao
    ) {
        super(converter, repository);
        this.credentialsDao = credentialsDao;
    }

    @Transactional
    public void delete(String userId){
        log.info("Deleting user {}", userId);
        repository.deleteById(userId);
        credentialsDao.deleteById(userId);
    }

    public SkyXpUser findUserByEmail(String email){
        return converter.convertEntity(repository.findByEmail(email));
    }

    @Transactional
    public void update(SkyXpUser user){
        Optional<UserEntity> actual = repository.findById(user.getUserId());
        if(actual.isPresent()){
            UserEntity entity = actual.get();
            entity.setUserId(user.getUserId());
            entity.setEmail(user.getEmail());
            entity.setRoles(user.getRoles());
        }else{
            throw new UserNotFoundException("User not found with id" + user.getUserId());
        }
    }
}
