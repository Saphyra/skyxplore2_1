package skyxplore.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.user.ChangeUserNameRequest;
import skyxplore.dataaccess.db.CredentialsDao;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.UserNameAlreadyExistsException;
import skyxplore.service.credentials.CredentialsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeUserNameService {
    private final CredentialsService credentialsService;

    public void changeUserName(ChangeUserNameRequest request, String userId) {
        Credentials credentials = credentialsService.getByUserId(userId);
        if (credentialsService.isUserNameExists(request.getNewUserName())) {
            throw new UserNameAlreadyExistsException(request.getNewUserName() + " username is already exists.");
        }
        if (!request.getPassword().equals(credentials.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        credentials.setUserName(request.getNewUserName());
        log.info("Changing username of user {}", userId);
        credentialsService.save(credentials);
        log.info("Username successfully changed.");
    }
}
