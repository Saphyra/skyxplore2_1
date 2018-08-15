package skyxplore.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.UserNotFoundException;

@SuppressWarnings("WeakerAccess")
@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class UserQueryService {
    private final UserDao userDao;

    public SkyXpUser getUserById(String userId) {
        SkyXpUser user = userDao.findById(userId);
        if(user == null){
            throw new UserNotFoundException("User not found with id" + userId);
        }
        return user;
    }

    public SkyXpUser getUserByName(String userName) {
        SkyXpUser user = userDao.findUserByUserName(userName);
        if(user == null){
            throw new UserNotFoundException("user not found with name " + userName);
        }
        return user;
    }

    public boolean isEmailExists(String email) {
        log.info("Someone wants to know is email {} is exists.", email);
        SkyXpUser user = userDao.findUserByEmail(email);
        return user != null;
    }

    public boolean isUserNameExists(String userName) {
        log.info("Someone wants to know is userName {} is exists.", userName);
        SkyXpUser user = userDao.findUserByUserName(userName);
        return user != null;
    }
}
