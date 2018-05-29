package skyxplore.home.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.home.controller.request.UserRegistrationRequest;
import skyxplore.dataaccess.user.UserDao;
import skyxplore.dataaccess.user.entity.Role;
import skyxplore.home.domain.view.converter.UserViewConverter;
import skyxplore.home.domain.SkyXpUser;
import skyxplore.home.exception.BadlyConfirmedPasswordException;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDao userDao;
    private final UserViewConverter userViewConverter;

    public SkyXpUser getUserByName(String userName){
        return userDao.findUserByUserName(userName);
    }

    public boolean isEmailExists(String email){
        SkyXpUser user = userDao.findUserByEmail(email);
        return user != null;
    }

    public boolean isUserNameExists(String userName){
        SkyXpUser user = userDao.findUserByUserName(userName);
        return user != null;
    }

    public Long registrateUser(UserRegistrationRequest request){
        validatePassword(request);
        SkyXpUser user = new SkyXpUser(request.getUsername(), request.getPassword(), request.getEmail(), new HashSet<Role>(Arrays.asList(Role.USER)));
        SkyXpUser registratedUser = userDao.registrateUser(user);
        log.info("New userId: {}", registratedUser.getUserId());
        return userViewConverter.convertDomain(registratedUser).getUserId();
    }

    private void validatePassword(UserRegistrationRequest request){
        if(!request.getPassword().equals(request.getConfirmPassword())){
            //TODO handle
            throw new BadlyConfirmedPasswordException();
        }
    }
}
