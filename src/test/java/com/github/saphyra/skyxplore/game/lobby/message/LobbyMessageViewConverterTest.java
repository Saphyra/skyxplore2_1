package com.github.saphyra.skyxplore.game.lobby.message;

import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessage;
import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessageView;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LobbyMessageViewConverterTest {
    private static final String SENDER_ID = "sender_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String MESSAGE = "lobbyMessage";
    private static final Long CREATED_AT = 5L;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private LobbyMessageViewConverter underTest;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private LobbyMessage lobbyMessage;

    @Test
    public void convertDomain() {
        //GIVEN
        given(lobbyMessage.getSender()).willReturn(SENDER_ID);
        given(lobbyMessage.getMessage()).willReturn(MESSAGE);
        given(lobbyMessage.getCreatedAt()).willReturn(CREATED_AT);

        given(characterQueryService.findByCharacterIdValidated(SENDER_ID)).willReturn(character);
        given(character.getCharacterName()).willReturn(CHARACTER_NAME);
        //WHEN
        LobbyMessageView result = underTest.convertDomain(lobbyMessage);
        //THEN
        assertThat(result.getSenderId()).isEqualTo(SENDER_ID);
        assertThat(result.getSenderName()).isEqualTo(CHARACTER_NAME);
        assertThat(result.getMessage()).isEqualTo(MESSAGE);
        assertThat(result.getCreatedAt()).isEqualTo(CREATED_AT);
    }
}