package skyxplore.service;

import lombok.RequiredArgsConstructor;

import org.github.saphyra.skyxplore.community.mail.MailFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
    private final CommunityFacade communityFacade;
    private final MailFacade mailFacade;

    public Integer getNumberOfFriendRequests(String characterId) {
        return communityFacade.getNumberOfFriendRequests(characterId);
    }

    public Integer getNumberOfUnreadMails(String characterId) {
        return mailFacade.getNumberOfUnreadMails(characterId);
    }

    public Integer getNumberOfNotifications(String characterId) {
        return getNumberOfFriendRequests(characterId) + getNumberOfUnreadMails(characterId);
    }
}
