package skyxplore.service.user;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.UserRegistrationRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.Role;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.exception.UserNameAlreadyExistsException;
import skyxplore.util.IdGenerator;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final IdGenerator idGenerator;
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    public void registrateUser(UserRegistrationRequest request) {
        validateRegistrationRequest(request);
        SkyXpUser user = new SkyXpUser(
            idGenerator.getRandomId(),
            request.getUsername(),
            request.getPassword(),
            request.getEmail(),
            new HashSet<>(Arrays.asList(Role.USER))
        );
        user.setRoles(new HashSet<>(Arrays.asList(Role.USER)));
        SkyXpUser registratedUser = userDao.registrateUser(user);
        log.info("New userId: {}", registratedUser.getUserId());
    }

    private void validateRegistrationRequest(UserRegistrationRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadlyConfirmedPasswordException("Confirm password does not match");
        }
        if (userQueryService.isUserNameExists(request.getUsername())) {
            throw new UserNameAlreadyExistsException(request.getUsername() + " user name is already exists.");
        }
        if (userQueryService.isEmailExists(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail() + " email is already exists.");
        }
    }
}
