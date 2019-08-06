package com.github.saphyra.skyxplore.userdata.community;

import com.github.saphyra.skyxplore.common.RequestConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {
    public static final String GET_NUMBER_OF_FRIEND_REQUESTS_MAPPING = API_PREFIX + "/notification/friend-request";
    public static final String GET_NUMBER_OF_UNREAD_MAILS_MAPPING = API_PREFIX + "/notification/unread-mail";
    public static final String GET_NUMBER_OF_NOTIFICATIONS_MAPPING = API_PREFIX + "/notification";

    private final NotificationFacade notificationFacade;

    @GetMapping(GET_NUMBER_OF_FRIEND_REQUESTS_MAPPING)
    Integer getNumberOfFriendRequests(
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know the number of his friend requests.", characterId);
        return notificationFacade.getNumberOfFriendRequests(characterId);
    }

    @GetMapping(GET_NUMBER_OF_UNREAD_MAILS_MAPPING)
    Integer getNumberOfUnreadMails(
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know the number of his unread mails.", characterId);
        return notificationFacade.getNumberOfUnreadMails(characterId);
    }

    @GetMapping(GET_NUMBER_OF_NOTIFICATIONS_MAPPING)
    Integer getNumberOfNotifications(
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know the number of notifications.", characterId);
        return notificationFacade.getNumberOfNotifications(characterId);
    }
}
