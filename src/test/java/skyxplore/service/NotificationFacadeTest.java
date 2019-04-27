package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;

@RunWith(MockitoJUnitRunner.class)
public class NotificationFacadeTest {
    @Mock
    private MailFacade mailFacade;

    @Mock
    private CommunityFacade communityFacade;

    @InjectMocks
    private NotificationFacade underTest;

    @Test
    public void testGetNumberOfUnreadMailsShouldCallFacadeAndReturn() {
        //GIVEN
        when(mailFacade.getNumberOfUnreadMails(CHARACTER_ID_1)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfUnreadMails(CHARACTER_ID_1);
        //THEN
        assertEquals(new Integer(2), result);
    }

    @Test
    public void testGetNumberOfFriendRequestShouldCallFacadeAndReturn() {
        //GIVEN
        when(communityFacade.getNumberOfFriendRequests(CHARACTER_ID_1)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID_1);
        //THEN
        assertEquals(new Integer(2), result);
    }

    @Test
    public void testGetNumberOfNotificationsShouldCalFacadeAndReturn() {
        //GIVEN
        when(communityFacade.getNumberOfFriendRequests(CHARACTER_ID_1)).thenReturn(2);
        when(mailFacade.getNumberOfUnreadMails(CHARACTER_ID_1)).thenReturn(3);
        //WHEN
        Integer result = underTest.getNumberOfNotifications(CHARACTER_ID_1);
        //THEN
        assertEquals(new Integer(5), result);
    }
}