package skyxplore.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skyxplore.service.AccessTokenFacade;

@SuppressWarnings("unused")
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class AccessTokenCleanup {
    private final AccessTokenFacade accessTokenFacade;

    @Scheduled(cron = "0 * * * * *")
    public void deleteOutDatedTokens(){
        log.info("Deleting outdated access tokens...");
        accessTokenFacade.deleteOutDatedTokens();
        log.info("Outdated access tokens successfully deleted.");
    }
}
