package skyxplore.service.accesstoken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.util.DateTimeUtil;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class AccessTokenCleanup {
    private final AccessTokenDao accessTokenDao;
    private final DateTimeUtil dateTimeUtil;

    @Scheduled(cron = "0 * * * * *")
    public void deleteOutDatedTokens() {
        log.info("Deleting outdated access tokens...");
        LocalDateTime expiration = dateTimeUtil.getExpirationDate();
        accessTokenDao.deleteExpired(dateTimeUtil.convertDomain(expiration));
        log.info("Outdated access tokens successfully deleted.");
    }
}
