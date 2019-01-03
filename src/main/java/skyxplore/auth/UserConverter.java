package skyxplore.auth;

import com.github.saphyra.authservice.domain.Credentials;
import com.github.saphyra.authservice.domain.User;
import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.CredentialsDao;
import skyxplore.domain.credentials.SkyXpCredentials;
import skyxplore.domain.user.SkyXpUser;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
//TODO unit test
public class UserConverter extends ConverterBase<SkyXpUser, User> {

    private final CredentialsDao credentialsDao;

    @Override
    public User processEntityConversion(SkyXpUser entity) {
        SkyXpCredentials skyXpCredentials = credentialsDao.getByUserId(entity.getUserId());
        return User.builder()
            .userId(entity.getUserId())
            .credentials(
                Credentials.builder()
                    .userName(skyXpCredentials.getUserName())
                    .password(skyXpCredentials.getPassword())
                    .build()
            )
            .roles(new HashSet<>())
            .build();
    }

    @Override
    protected SkyXpUser processDomainConversion(User domain) {
        throw new UnsupportedOperationException("User cannot be converted to SkyXpUser.");
    }
}
