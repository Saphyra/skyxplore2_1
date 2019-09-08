package com.github.saphyra.skyxplore.userdata.community;

import com.github.saphyra.skyxplore.userdata.community.friendship.service.FriendRequestQueryService;
import com.github.saphyra.skyxplore.userdata.community.mail.MailQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
    private final FriendRequestQueryService friendRequestQueryService;
    private final MailQueryService mailQueryService;

    Integer getNumberOfFriendRequests(String characterId) {
        return friendRequestQueryService.getNumberOfFriendRequests(characterId);
    }

    public Integer getNumberOfUnreadMails(String characterId) {
        return mailQueryService.getNumberOfUnreadMails(characterId);
    }

    Integer getNumberOfNotifications(String characterId) {
        return getNumberOfFriendRequests(characterId) + getNumberOfUnreadMails(characterId);
    }
}
