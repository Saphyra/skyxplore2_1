package com.github.saphyra.skyxplore.game.lobby.message;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessageView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LobbyLobbyMessageControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String MESSAGE = "message";

    @Mock
    private LobbyMessageSenderService lobbyMessageSenderService;

    @Mock
    private LobbyMessageViewQueryService lobbyMessageViewQueryService;

    @InjectMocks
    private LobbyMessageController underTest;

    @Mock
    private LobbyMessageView lobbyMessageView;

    @Test
    public void getMessage() {
        //GIVEN
        given(lobbyMessageViewQueryService.getMessages(CHARACTER_ID, true)).willReturn(Arrays.asList(lobbyMessageView));
        //WHEN
        ResponseEntity<List<LobbyMessageView>> result = underTest.getMessages(CHARACTER_ID, true);
        //THEN
        assertThat(result.getBody()).containsOnly(lobbyMessageView);
    }

    @Test
    public void sendMessage() {
        //WHEN
        underTest.sendMessage(CHARACTER_ID, new OneStringParamRequest(MESSAGE));
        //THEN
        verify(lobbyMessageSenderService).sendMessage(CHARACTER_ID, MESSAGE);
    }
}