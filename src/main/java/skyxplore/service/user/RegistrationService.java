package skyxplore.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.user.UserRegistrationRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.user.Role;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.exception.UserNameAlreadyExistsException;
import skyxplore.service.credentials.CredentialsService;
import skyxplore.util.IdGenerator;

import java.util.Arrays;
import java.util.HashSet;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final CredentialsService credentialsService;
    private final IdGenerator idGenerator;
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    public void registrateUser(UserRegistrationRequest request) {
        validateRegistrationRequest(request);
        SkyXpUser user = new SkyXpUser(
            idGenerator.getRandomId(),
            request.getEmail(),
            new HashSet<>(Arrays.asList(Role.USER))
        );
        userDao.registrateUser(user);
        credentialsService.save(new Credentials(user.getUserId(), request.getUsername(), request.getPassword()));
        log.info("New userId: {}", user.getUserId());
    }

    private void validateRegistrationRequest(UserRegistrationRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadlyConfirmedPasswordException("Confirm password does not match");
        }
        if (credentialsService.isUserNameExists(request.getUsername())) {
            throw new UserNameAlreadyExistsException(request.getUsername() + " user name is already exists.");
        }
        if (userQueryService.isEmailExists(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail() + " email is already exists.");
        }
    }
}
