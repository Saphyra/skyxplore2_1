package skyxplore.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.ChangeUserNameRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.UserNameAlreadyExistsException;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ChangeUserNameService {
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    public void changeUserName(ChangeUserNameRequest request, String userId) {
        SkyXpUser user = userQueryService.getUserById(userId);
        if (userQueryService.isUserNameExists(request.getNewUserName())) {
            throw new UserNameAlreadyExistsException(request.getNewUserName() + " username is already exists.");
        }
        if (!request.getPassword().equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        user.setUsername(request.getNewUserName());
        log.info("Changing username of user {}", userId);
        userDao.update(user);
        log.info("Username successfully changed.");
    }
}
