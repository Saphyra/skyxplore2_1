package org.github.saphyra.skyxplore.community.friendship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipFacadeTest {
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @InjectMocks
    private FriendshipFacade underTest;

    @Test
    public void testGetNumberOfFriendRequestsShouldCallServiceAndResult() {
        //GIVEN
        when(friendshipQueryService.getNumberOfFriendRequests(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID);
        //THEN
        verify(friendshipQueryService).getNumberOfFriendRequests(CHARACTER_ID);
        assertThat(result).isEqualTo(2);
    }
}