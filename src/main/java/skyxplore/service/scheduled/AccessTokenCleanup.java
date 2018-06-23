package skyxplore.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skyxplore.service.AccessTokenService;

@SuppressWarnings("unused")
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class AccessTokenCleanup {
    private final AccessTokenService accessTokenService;

    @Scheduled(cron = "0 * * * * *")
    public void deleteOutDatedTokens(){
        log.info("Deleting outdated access tokens...");
        accessTokenService.deleteOutDatedTokens();
        log.info("Outdated access tokens successfully deleted.");
    }
}
