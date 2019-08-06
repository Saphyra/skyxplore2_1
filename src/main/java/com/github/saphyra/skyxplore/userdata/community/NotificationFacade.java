package com.github.saphyra.skyxplore.userdata.community;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.userdata.community.friendship.FriendshipQueryService;
import com.github.saphyra.skyxplore.userdata.community.mail.MailQueryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
    private final FriendshipQueryService friendshipQueryService;
    private final MailQueryService mailQueryService;

    public Integer getNumberOfFriendRequests(String characterId) {
        return friendshipQueryService.getNumberOfFriendRequests(characterId);
    }

    public Integer getNumberOfUnreadMails(String characterId) {
        return mailQueryService.getNumberOfUnreadMails(characterId);
    }

    public Integer getNumberOfNotifications(String characterId) {
        return getNumberOfFriendRequests(characterId) + getNumberOfUnreadMails(characterId);
    }
}
