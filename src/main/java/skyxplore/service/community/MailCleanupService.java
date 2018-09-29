package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.util.DateTimeUtil;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
//TODO unit test
public class MailCleanupService {
    private static final Integer EXPIRATION_IN_DAYS = 30;

    private final DateTimeUtil dateTimeUtil;
    private final MailDao mailDao;

    @Scheduled(fixedDelay = 1000*60*60L)
    public void deleteExpiredMails(){
        log.info("Deleting deleted mails...");
        mailDao.deleteBothSideDeleted();
        log.info("Deleting expired mails...");
        mailDao.deleteExpired(getExpiration());
    }

    private Long getExpiration() {
        LocalDateTime now = dateTimeUtil.now();
        LocalDateTime expiration = now.minusDays(EXPIRATION_IN_DAYS);
        return dateTimeUtil.convertDomain(expiration);
    }
}
