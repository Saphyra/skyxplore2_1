package org.github.saphyra.skyxplore.community;

import org.github.saphyra.skyxplore.community.friendship.FriendshipFacade;
import org.github.saphyra.skyxplore.community.mail.MailFacade;
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
    private MailFacade mailFacade;

    @Mock
    private FriendshipFacade friendshipFacade;

    @InjectMocks
    private NotificationFacade underTest;

    @Test
    public void testGetNumberOfUnreadMailsShouldCallFacadeAndReturn() {
        //GIVEN
        when(mailFacade.getNumberOfUnreadMails(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfUnreadMails(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testGetNumberOfFriendRequestShouldCallFacadeAndReturn() {
        //GIVEN
        when(friendshipFacade.getNumberOfFriendRequests(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testGetNumberOfNotificationsShouldCalFacadeAndReturn() {
        //GIVEN
        when(friendshipFacade.getNumberOfFriendRequests(CHARACTER_ID)).thenReturn(2);
        when(mailFacade.getNumberOfUnreadMails(CHARACTER_ID)).thenReturn(3);
        //WHEN
        Integer result = underTest.getNumberOfNotifications(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(5);
    }
}