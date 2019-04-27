package skyxplore.service.user;

import com.github.saphyra.encryption.impl.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.cache.UserNameCache;
import skyxplore.controller.request.user.ChangeUserNameRequest;
import org.github.saphyra.skyxplore.user.domain.credentials.SkyXpCredentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.UserNameAlreadyExistsException;
import skyxplore.service.credentials.CredentialsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeUserNameService {
    private final PasswordService passwordService;
    private final CredentialsService credentialsService;
    private final UserNameCache userNameCache;

    public void changeUserName(ChangeUserNameRequest request, String userId) {
        SkyXpCredentials skyXpCredentials = credentialsService.getByUserId(userId);
        if (credentialsService.isUserNameExists(request.getNewUserName())) {
            throw new UserNameAlreadyExistsException(request.getNewUserName() + " username is already exists.");
        }
        if (!passwordService.authenticate(request.getPassword(), skyXpCredentials.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        skyXpCredentials.setUserName(request.getNewUserName());
        log.info("Changing username of user {}", userId);
        credentialsService.save(skyXpCredentials);
        log.info("Username successfully changed.");

        userNameCache.invalidate(request.getNewUserName());
    }
}
