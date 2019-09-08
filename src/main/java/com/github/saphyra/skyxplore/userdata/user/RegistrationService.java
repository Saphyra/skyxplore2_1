package com.github.saphyra.skyxplore.userdata.user;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.user.domain.Role;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest;
import com.github.saphyra.skyxplore.userdata.user.repository.user.UserDao;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        SkyXpUser user = SkyXpUser.builder()
            .userId(idGenerator.generateRandomId())
            .email(request.getEmail())
            .roles(new HashSet<>(Arrays.asList(Role.USER)))
            .build();

        userDao.save(user);
        String passwordToken = passwordService.hashPassword(request.getPassword());
        SkyXpCredentials credentials = SkyXpCredentials.builder()
            .userId(user.getUserId())
            .userName(request.getUsername())
            .password(passwordToken)
            .build();
        credentialsService.save(credentials);
        log.info("New userId: {}", user.getUserId());
    }

    private void validateRegistrationRequest(UserRegistrationRequest request) {
        if (credentialsService.isUserNameExists(request.getUsername())) {
            throw ExceptionFactory.userNameAlreadyExists(request.getUsername());
        }
        if (userQueryService.isEmailExists(request.getEmail())) {
            throw ExceptionFactory.emailAlreadyExists(request.getEmail());
        }
    }
}
