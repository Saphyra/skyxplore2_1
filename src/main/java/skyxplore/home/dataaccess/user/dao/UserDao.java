package skyxplore.home.dataaccess.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import skyxplore.home.dataaccess.converter.UserConverter;
import skyxplore.home.dataaccess.user.entity.UserEntity;
import skyxplore.home.dataaccess.user.repository.UserRepository;
import skyxplore.home.service.domain.SkyXpUser;

@Component
@RequiredArgsConstructor
public class UserDao implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public SkyXpUser findUserByUserName(String userName){
        return userConverter.convert(userRepository.findByUsername(userName));
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(userName);
        UserDetails details = new User(user.getUsername(), user.getPassword(), user.getRoles());
        return details;
    }
}
