package skyxplore.dataaccess.db;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.UserRepository;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.domain.user.UserEntity;

import javax.transaction.Transactional;

@Component
@Slf4j
public class UserDao extends AbstractDao<UserEntity, SkyXpUser, String, UserRepository> {
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
}
