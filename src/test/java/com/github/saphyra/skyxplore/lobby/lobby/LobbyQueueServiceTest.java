package com.github.saphyra.skyxplore.lobby.lobby;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LobbyQueueServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String OWNER_ID = "owner_id";

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private LobbyQueueService underTest;

    @Mock
    private Lobby lobby;

    @Before
    public void setUp() {
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);
        given(lobby.getOwnerId()).willReturn(CHARACTER_ID);
    }

    @Test(expected = ForbiddenException.class)
    public void startQueue_notOwnerStarts() {
        //GIVEN
        given(lobby.getOwnerId()).willReturn(OWNER_ID);
        //WHEN
        underTest.startQueue(CHARACTER_ID, true);
    }

    @Test
    public void startQueue() {
        //WHEN
        underTest.startQueue(CHARACTER_ID, true);
        //THEN
        verify(lobby).startQueueing(true);
    }

}