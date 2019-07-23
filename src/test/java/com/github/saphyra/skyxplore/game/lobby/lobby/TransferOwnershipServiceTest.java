package com.github.saphyra.skyxplore.game.lobby.lobby;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class TransferOwnershipServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String NEW_OWNER_ID = "new_owner_id";

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private TransferOwnershipService underTest;

    @Mock
    private Lobby lobby;

    @Test
    public void transferOwnership() {
        //GIVEN
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);
        //WHEN
        underTest.transferOwnership(CHARACTER_ID, NEW_OWNER_ID);
        //THEN
        verify(lobby).transferOwnership(CHARACTER_ID, NEW_OWNER_ID);
    }
}