package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.user.UserDao;
import skyxplore.dataaccess.user.entity.Role;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.exception.UserNameAlreadyExistsException;
import skyxplore.restcontroller.request.ChangeEmailRequest;
import skyxplore.restcontroller.request.ChangePasswordRequest;
import skyxplore.restcontroller.request.ChangeUserNameRequest;
import skyxplore.restcontroller.request.UserRegistrationRequest;
import skyxplore.restcontroller.view.converter.UserViewConverter;
import skyxplore.service.domain.SkyXpUser;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDao userDao;
    private final UserViewConverter userViewConverter;

    public void changeEmail(ChangeEmailRequest request, Long userId){
        SkyXpUser user = getUserById(userId);
        if(isEmailExists(request.getNewEmail())){
            throw new EmailAlreadyExistsException();
        }
        if(!request.getPassword().equals(user.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }
        user.setEmail(request.getNewEmail());
        log.info("Changeing email of user {}", userId);
        userDao.update(user);
        log.info("Email changed successfully.");
    }

    public void changePassword(ChangePasswordRequest request, Long uid){
        SkyXpUser user = getUserById(uid);
        validateChangePasswordRequest(request, user);
        user.setPassword(request.getNewPassword());
        log.info("Changing password of user " + uid);
        userDao.update(user);
        log.info("Password successfully changed.");
    }

    public void changeUserName(ChangeUserNameRequest request, Long userId){
        SkyXpUser user = getUserById(userId);
        if(isUserNameExists(request.getNewUserName())){
            throw new UserNameAlreadyExistsException();
        }
        if(!request.getPassword().equals(user.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }
        user.setUsername(request.getNewUserName());
        log.info("Changing username of user {}", userId);
        userDao.update(user);
        log.info("Username successfully changed.");
    }

    public SkyXpUser getUserById(Long userId){
        return userDao.findById(userId);
    }

    public SkyXpUser getUserByName(String userName){
        return userDao.findUserByUserName(userName);
    }

    public boolean isEmailExists(String email){
        log.info("Someone wants to know is email {} is exists.", email);
        SkyXpUser user = userDao.findUserByEmail(email);
        return user != null;
    }

    public boolean isUserNameExists(String userName){
        log.info("Someone wants to know is userName {} is exists.", userName);
        SkyXpUser user = userDao.findUserByUserName(userName);
        return user != null;
    }

    public Long registrateUser(UserRegistrationRequest request){
        validateRegistrationRequest(request);
        SkyXpUser user = new SkyXpUser(request.getUsername(), request.getPassword(), request.getEmail(), new HashSet<Role>(Arrays.asList(Role.USER)));
        SkyXpUser registratedUser = userDao.registrateUser(user);
        log.info("New userId: {}", registratedUser.getUserId());
        return userViewConverter.convertDomain(registratedUser).getUserId();
    }

    private void validateChangePasswordRequest(ChangePasswordRequest request, SkyXpUser user){
        if(!user.getPassword().equals(request.getOldPassword())){
            throw new BadCredentialsException("Wrong password.");
        }
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new BadlyConfirmedPasswordException();
        }
    }

    private void validateRegistrationRequest(UserRegistrationRequest request){
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
