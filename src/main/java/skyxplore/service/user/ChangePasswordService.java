package skyxplore.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.ChangePasswordRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadlyConfirmedPasswordException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangePasswordService {
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    public void changePassword(ChangePasswordRequest request, String userId) {
        SkyXpUser user = userQueryService.getUserById(userId);
        validateChangePasswordRequest(request, user);
        user.setPassword(request.getNewPassword());
        log.info("Changing password of user " + userId);
        userDao.update(user);
        log.info("Password successfully changed.");
    }

    private void validateChangePasswordRequest(ChangePasswordRequest request, SkyXpUser user) {
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadlyConfirmedPasswordException("Confirm password does not match.");
        }
    }
}
