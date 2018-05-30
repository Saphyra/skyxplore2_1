package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.restcontroller.request.UserRegistrationRequest;
import skyxplore.dataaccess.user.UserDao;
import skyxplore.dataaccess.user.entity.Role;
import skyxplore.restcontroller.view.converter.UserViewConverter;
import skyxplore.service.domain.SkyXpUser;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.exception.UserNameAlreadyExistsException;

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
            throw new BadlyConfirmedPasswordException();
        }
        if(isUserNameExists(request.getUsername())){
            throw new UserNameAlreadyExistsException();
        }
        if(isEmailExists(request.getEmail())){
            throw new EmailAlreadyExistsException();
        }
    }
}
