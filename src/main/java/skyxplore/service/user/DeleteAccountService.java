package skyxplore.service.user;

import com.github.saphyra.encryption.impl.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.user.AccountDeleteRequest;
import skyxplore.dataaccess.db.CharacterDao;
import org.github.saphyra.skyxplore.user.UserDao;
import org.github.saphyra.skyxplore.user.domain.credentials.SkyXpCredentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.service.credentials.CredentialsService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteAccountService {
    private final PasswordService passwordService;
    private final CharacterDao characterDao;
    private final CredentialsService credentialsService;
    private final UserDao userDao;

    @Transactional
    public void deleteAccount(AccountDeleteRequest request, String userId) {
        SkyXpCredentials skyXpCredentials = credentialsService.getByUserId(userId);
        if (!passwordService.authenticate(request.getPassword(), skyXpCredentials.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        characterDao.deleteByUserId(userId);
        userDao.delete(userId);
    }
}
