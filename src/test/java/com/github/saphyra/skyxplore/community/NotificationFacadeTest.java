package com.github.saphyra.skyxplore.community;

import com.github.saphyra.skyxplore.community.friendship.FriendshipQueryService;
import com.github.saphyra.skyxplore.community.mail.MailQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationFacadeTest {
    private static final String CHARACTER_ID = "character_id";
    @Mock
    private MailQueryService mailQueryService;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @InjectMocks
    private NotificationFacade underTest;

    @Test
    public void testGetNumberOfUnreadMailsShouldCallFacadeAndReturn() {
        //GIVEN
        when(mailQueryService.getNumberOfUnreadMails(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfUnreadMails(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testGetNumberOfFriendRequestShouldCallFacadeAndReturn() {
        //GIVEN
        when(friendshipQueryService.getNumberOfFriendRequests(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testGetNumberOfNotificationsShouldCalFacadeAndReturn() {
        //GIVEN
        when(friendshipQueryService.getNumberOfFriendRequests(CHARACTER_ID)).thenReturn(2);
        when(mailQueryService.getNumberOfUnreadMails(CHARACTER_ID)).thenReturn(3);
        //WHEN
        Integer result = underTest.getNumberOfNotifications(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(5);
    }
}