package com.github.saphyra.skyxplore.userdata.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationControllerTest {
    private static final String CHARACTER_ID = "character_id";
    @Mock
    private NotificationFacade notificationFacade;

    @InjectMocks
    private NotificationController underTest;

    @Test
    public void testGetNumberOfFriendRequests() {
        //GIVEN
        when(notificationFacade.getNumberOfFriendRequests(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testGetNumberOfUnreadMails() {
        //GIVEN
        when(notificationFacade.getNumberOfUnreadMails(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfUnreadMails(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testGetNumberOfNotifications() {
        //GIVEN
        when(notificationFacade.getNumberOfNotifications(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfNotifications(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(2);
    }
}