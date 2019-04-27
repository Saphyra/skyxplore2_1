package skyxplore.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserQueryService {
    private final UserDao userDao;

    public SkyXpUser getUserById(String userId) {
        return userDao.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id" + userId));
    }

    public boolean isEmailExists(String email) {
        log.info("Someone wants to know is email {} is exists.", email);
        return userDao.findUserByEmail(email) != null;
    }
}
