package org.github.saphyra.skyxplore.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.user.repository.user.UserDao;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.github.saphyra.skyxplore.common.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserQueryService {
    private final UserDao userDao;

    SkyXpUser getUserById(String userId) {
        return userDao.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id" + userId));
    }

    public boolean isEmailExists(String email) {
        log.info("Someone wants to know is email {} is exists.", email);
        return userDao.findUserByEmail(email) != null;
    }
}
