package com.github.saphyra.skyxplore.game.lobby.message;

import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessage;
import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessageView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LobbyMessageViewQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String SENDER_ID = "sender_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String MESSAGE_1 = "message_1";
    private static final String MESSAGE_2 = "message_2";
    private static final Long CREATED_AT = 6461L;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @Mock
    private LobbyMessageViewConverter lobbyMessageViewConverter;

    @InjectMocks
    private LobbyMessageViewQueryService underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private LobbyMessage queriedLobbyMessage;

    @Mock
    private LobbyMessage lobbyMessage;

    @Mock
    private LobbyMessageView lobbyMessageView;

    @Before
    public void setup() {
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);
        given(lobby.getLobbyMessages()).willReturn(Arrays.asList(lobbyMessage, queriedLobbyMessage));

        given(queriedLobbyMessage.getQueriedBy()).willReturn(Arrays.asList(CHARACTER_ID));

        given(lobbyMessage.getQueriedBy()).willReturn(Collections.emptyList());


        given(lobbyMessageViewConverter.convertDomain(lobbyMessage)).willReturn(lobbyMessageView);
        given(lobbyMessageViewConverter.convertDomain(queriedLobbyMessage)).willReturn(lobbyMessageView);
    }

    @Test
    public void getMessages() {
        //WHEN
        List<LobbyMessageView> result = underTest.getMessages(CHARACTER_ID, false);
        //THEN
        verify(lobbyMessage).addQueriedBy(CHARACTER_ID);

        assertThat(result).containsExactly(lobbyMessageView);
    }

    @Test
    public void getMessages_queryAll() {
        //WHEN
        List<LobbyMessageView> result = underTest.getMessages(CHARACTER_ID, true);
        //THEN
        verify(lobbyMessage).addQueriedBy(CHARACTER_ID);
        verify(queriedLobbyMessage).addQueriedBy(CHARACTER_ID);

        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(lobbyMessageView);
    }
}