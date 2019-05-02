package org.github.saphyra.skyxplore.community;

import lombok.RequiredArgsConstructor;

import org.github.saphyra.skyxplore.community.friendship.FriendshipFacade;
import org.github.saphyra.skyxplore.community.mail.MailFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
    private final FriendshipFacade friendshipFacade;
    private final MailFacade mailFacade;

    public Integer getNumberOfFriendRequests(String characterId) {
        return friendshipFacade.getNumberOfFriendRequests(characterId);
    }

    public Integer getNumberOfUnreadMails(String characterId) {
        return mailFacade.getNumberOfUnreadMails(characterId);
    }

    public Integer getNumberOfNotifications(String characterId) {
        return getNumberOfFriendRequests(characterId) + getNumberOfUnreadMails(characterId);
    }
}
