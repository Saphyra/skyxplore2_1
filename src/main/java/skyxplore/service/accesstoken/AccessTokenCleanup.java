package skyxplore.service.accesstoken;

import java.util.Calendar;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.util.AccessTokenDateResolver;

@SuppressWarnings("unused")
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class AccessTokenCleanup {
    private final AccessTokenDao accessTokenDao;
    private final AccessTokenDateResolver accessTokenDateResolver;

    @Scheduled(cron = "0 * * * * *")
    public void deleteOutDatedTokens() {
        log.info("Deleting outdated access tokens...");
        Calendar expiration = accessTokenDateResolver.getExpirationDate();
        accessTokenDao.deleteExpired(expiration);
        log.info("Outdated access tokens successfully deleted.");
    }
}
