package com.github.saphyra.skyxplore.userdata.user;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.userdata.user.repository.user.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserQueryService {
    private final UserDao userDao;

    SkyXpUser getUserById(String userId) {
        return userDao.findById(userId)
            .orElseThrow(() -> ExceptionFactory.userNotFound(userId));
    }

    public boolean isEmailExists(String email) {
        log.info("Someone wants to know is email {} is exists.", email);
        return userDao.findUserByEmail(email).isPresent();
    }
}
