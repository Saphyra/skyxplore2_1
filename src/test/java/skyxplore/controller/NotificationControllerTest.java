package skyxplore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.service.NotificationFacade;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;

@RunWith(MockitoJUnitRunner.class)
public class NotificationControllerTest {
    @Mock
    private NotificationFacade notificationFacade;

    @InjectMocks
    private NotificationController underTest;

    @Test
    public void testGetNumberOfFriendRequests() {
        //GIVEN
        when(notificationFacade.getNumberOfFriendRequests(CHARACTER_ID_1)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID_1);
        //THEN
        assertEquals((Integer) 2, result);
    }

    @Test
    public void testGetNumberOfUnreadMails() {
        //GIVEN
        when(notificationFacade.getNumberOfUnreadMails(CHARACTER_ID_1)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfUnreadMails(CHARACTER_ID_1);
        //THEN
        assertEquals((Integer) 2, result);
    }

    @Test
    public void testGetNumberOfNotifications() {
        //GIVEN
        when(notificationFacade.getNumberOfNotifications(CHARACTER_ID_1)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfNotifications(CHARACTER_ID_1);
        //THEN
        assertEquals((Integer) 2, result);
    }
}