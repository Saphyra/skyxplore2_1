package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.community.mail.repository.MailDao;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
class MailCleanupService {
    private static final Integer EXPIRATION_IN_DAYS = 30;

    private final DateTimeUtil dateTimeUtil;
    private final MailDao mailDao;

    @Scheduled(fixedDelay = 1000 * 60 * 60L)
    void deleteExpiredMails() {
        log.info("Deleting deleted mails...");
        mailDao.deleteBothSideDeleted();
        log.info("Deleting expired mails...");
        mailDao.deleteExpired(getExpiration());
    }

    private Long getExpiration() {
        return dateTimeUtil.convertDomain(dateTimeUtil.now().minusDays(EXPIRATION_IN_DAYS));
    }
}
