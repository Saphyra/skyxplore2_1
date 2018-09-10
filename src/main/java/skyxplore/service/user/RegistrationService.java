package skyxplore.service.user;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.user.UserRegistrationRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.user.Role;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.encryption.base.PasswordService;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.exception.UserNameAlreadyExistsException;
import skyxplore.service.credentials.CredentialsService;
import skyxplore.util.IdGenerator;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final CredentialsService credentialsService;
    private final IdGenerator idGenerator;
    private final PasswordService passwordService;
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    public void registrateUser(UserRegistrationRequest request) {
        validateRegistrationRequest(request);
        SkyXpUser user = new SkyXpUser(
            idGenerator.getRandomId(),
            request.getEmail(),
            new HashSet<>(Arrays.asList(Role.USER))
        );
        userDao.save(user);
        String passwordToken = passwordService.hashPassword(request.getPassword());
        credentialsService.save(new Credentials(user.getUserId(), request.getUsername(), passwordToken));
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
