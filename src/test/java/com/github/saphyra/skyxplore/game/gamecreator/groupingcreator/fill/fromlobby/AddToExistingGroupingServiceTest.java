package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMember;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddToExistingGroupingServiceTest {
    private static final int MAX_GROUP_SIZE = 324;
    private static final String CHARACTER_ID = "character_id";
    private static final UUID LOBBY_ID = UUID.randomUUID();

    @Mock
    private GameGroupFactory gameGroupFactory;

    @InjectMocks
    private AddToExistingGroupingService underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private GameGrouping gameGrouping;

    @Mock
    private LobbyMember lobbyMember;

    @Mock
    private GameGroup gameGroup;

    @Test
    public void addToExistingGrouping() {
        //GIVEN
        given(lobby.getMembers()).willReturn(Arrays.asList(lobbyMember));
        given(lobby.isAutoFill()).willReturn(true);
        given(lobby.getLobbyId()).willReturn(LOBBY_ID);
        given(lobbyMember.getCharacterId()).willReturn(CHARACTER_ID);
        given(gameGroupFactory.createGroup(any(), eq(true), eq(MAX_GROUP_SIZE))).willReturn(gameGroup);
        //WHEN
        underTest.addToExistingGrouping(lobby, gameGrouping, MAX_GROUP_SIZE);
        //THEN
        verify(gameGrouping).addGroup(gameGroup);
        verify(gameGrouping).lockLobby(LOBBY_ID);
    }
}