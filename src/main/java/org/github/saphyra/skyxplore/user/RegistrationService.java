package org.github.saphyra.skyxplore.user;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.user.domain.Role;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.github.saphyra.skyxplore.user.repository.user.UserDao;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.user.domain.UserRegistrationRequest;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.exception.UserNameAlreadyExistsException;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
class RegistrationService {
    private final CredentialsService credentialsService;
    private final IdGenerator idGenerator;
    private final PasswordService passwordService;
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    void registerUser(UserRegistrationRequest request) {
        validateRegistrationRequest(request);
        SkyXpUser user = new SkyXpUser(
            idGenerator.generateRandomId(),
            request.getEmail(),
            new HashSet<>(Arrays.asList(Role.USER))
        );
        userDao.save(user);
        String passwordToken = passwordService.hashPassword(request.getPassword());
        credentialsService.save(new SkyXpCredentials(user.getUserId(), request.getUsername(), passwordToken));
        log.info("New userId: {}", user.getUserId());
    }

    private void validateRegistrationRequest(UserRegistrationRequest request) {
        if (credentialsService.isUserNameExists(request.getUsername())) {
            throw new UserNameAlreadyExistsException(request.getUsername() + " user name is already exists.");
        }
        if (userQueryService.isEmailExists(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail() + " email is already exists.");
        }
    }
}
