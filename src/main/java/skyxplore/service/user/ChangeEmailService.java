package skyxplore.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.user.ChangeEmailRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.exception.base.UnauthorizedException;
import skyxplore.service.credentials.CredentialsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeEmailService {
    private final CredentialsService credentialsService;
    private final UserQueryService userQueryService;
    private final UserDao userDao;

    public void changeEmail(ChangeEmailRequest request, String userId) {
        SkyXpUser user = userQueryService.getUserById(userId);
        if (userQueryService.isEmailExists(request.getNewEmail())) {
            throw new EmailAlreadyExistsException(request.getNewEmail() + " email is already exists.");
        }

        Credentials credentials = credentialsService.getByUserId(userId);
        if(credentials == null){
            throw new UnauthorizedException("No credentials found with userId " + userId);
        }
        if (!request.getPassword().equals(credentials.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        user.setEmail(request.getNewEmail());
        log.info("Changeing email of user {}", userId);
        userDao.update(user);
        log.info("Email changed successfully.");
    }
}
