package skyxplore.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.user.AccountDeleteRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.credentials.Credentials;
import skyxplore.encryption.base.PasswordService;
import skyxplore.exception.BadCredentialsException;
import skyxplore.service.credentials.CredentialsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteAccountService {
    private final PasswordService passwordService;
    private final CharacterDao characterDao;
    private final CredentialsService credentialsService;
    private final UserDao userDao;

    public void deleteAccount(AccountDeleteRequest request, String userId) {
        Credentials credentials = credentialsService.getByUserId(userId);
        if (!passwordService.authenticate(request.getPassword(), credentials.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        characterDao.deleteByUserId(userId);
        userDao.delete(userId);
    }
}
