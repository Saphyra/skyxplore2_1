package com.github.saphyra.skyxplore.game.lobby.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.domain.message.MessageView;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String MESSAGE = "message";

    @Mock
    private MessageSenderService messageSenderService;

    @Mock
    private LobbyMessageViewQueryService lobbyMessageViewQueryService;

    @InjectMocks
    private MessageController underTest;

    @Mock
    private MessageView messageView;

    @Test
    public void getMessage() {
        //GIVEN
        given(lobbyMessageViewQueryService.getMessages(CHARACTER_ID, true)).willReturn(Arrays.asList(messageView));
        //WHEN
        List<MessageView> result = underTest.getMessages(CHARACTER_ID, true);
        //THEN
        assertThat(result).containsOnly(messageView);
    }

    @Test
    public void sendMessage() {
        //WHEN
        underTest.sendMessage(CHARACTER_ID, new OneStringParamRequest(MESSAGE));
        //THEN
        verify(messageSenderService).sendMessage(CHARACTER_ID, MESSAGE);
    }
}