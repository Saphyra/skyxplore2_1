package skyxplore.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.AccountDeleteRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class DeleteAccountService {
    private final  CharacterDao characterDao;
    private final UserQueryService userQueryService;
    private final UserDao userDao;

    public void deleteAccount(AccountDeleteRequest request, String userId) {
        SkyXpUser user = userQueryService.getUserById(userId);
        if (!request.getPassword().equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        characterDao.deleteByUserId(userId);
        userDao.delete(userId);
    }
}
