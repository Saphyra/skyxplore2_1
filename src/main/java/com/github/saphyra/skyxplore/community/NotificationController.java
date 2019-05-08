package com.github.saphyra.skyxplore.community;

import com.github.saphyra.skyxplore.filter.CustomFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
class NotificationController {
    private static final String GET_NUMBER_OF_FRIEND_REQUESTS_MAPPING = "notification/friend-request";
    private static final String GET_NUMBER_OF_UNREAD_MAILS_MAPPING = "notification/unread-mail";
    private static final String GET_NUMBER_OF_NOTIFICATIONS = "notification";

    private final NotificationFacade notificationFacade;

    @GetMapping(GET_NUMBER_OF_FRIEND_REQUESTS_MAPPING)
    Integer getNumberOfFriendRequests(
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know the number of his friend requests.", characterId);
        return notificationFacade.getNumberOfFriendRequests(characterId);
    }

    @GetMapping(GET_NUMBER_OF_UNREAD_MAILS_MAPPING)
    Integer getNumberOfUnreadMails(
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know the number of his unread mails.", characterId);
        return notificationFacade.getNumberOfUnreadMails(characterId);
    }

    @GetMapping(GET_NUMBER_OF_NOTIFICATIONS)
    Integer getNumberOfNotifications(
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know the number of notifications.", characterId);
        return notificationFacade.getNumberOfNotifications(characterId);
    }
}
