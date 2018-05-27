package skyxplore.home.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.home.controller.request.UserRegistrationRequest;
import skyxplore.home.dataaccess.user.dao.UserDao;
import skyxplore.home.dataaccess.user.entity.Role;
import skyxplore.home.service.domain.SkyXpUser;
import skyxplore.home.service.exception.BadlyConfirmedPasswordException;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDao userDao;

    public boolean isUserNameExists(String userName){
        log.info("Someone wants to know if username {} exists.", userName);
        SkyXpUser user = userDao.findUserByUserName(userName);
        return user != null;
    }

    public void registrateUser(UserRegistrationRequest request){
        log.info("Received request: {}", request);
        validatePassword(request);
        SkyXpUser user = new SkyXpUser(request.getUsername(), request.getPassword(), request.getEmail(), new HashSet<Role>(Arrays.asList(Role.USER)));

    }

    private void validatePassword(UserRegistrationRequest request){
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new BadlyConfirmedPasswordException();
        }
    }
}
