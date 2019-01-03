package skyxplore.service.user;

import com.github.saphyra.encryption.impl.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.user.ChangeUserNameRequest;
import skyxplore.domain.credentials.Credentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.UserNameAlreadyExistsException;
import skyxplore.service.credentials.CredentialsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeUserNameService {
    private final PasswordService passwordService;
    private final CredentialsService credentialsService;

    public void changeUserName(ChangeUserNameRequest request, String userId) {
        Credentials credentials = credentialsService.getByUserId(userId);
        if (credentialsService.isUserNameExists(request.getNewUserName())) {
            throw new UserNameAlreadyExistsException(request.getNewUserName() + " username is already exists.");
        }
        if (!passwordService.authenticate(request.getPassword(), credentials.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        credentials.setUserName(request.getNewUserName());
        log.info("Changing username of user {}", userId);
        credentialsService.save(credentials);
        log.info("Username successfully changed.");
    }
}
