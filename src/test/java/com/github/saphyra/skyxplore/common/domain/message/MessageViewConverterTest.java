package com.github.saphyra.skyxplore.common.domain.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;

@RunWith(MockitoJUnitRunner.class)
public class MessageViewConverterTest {
    private static final String SENDER_ID = "sender_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String MESSAGE = "message";
    private static final Long CREATED_AT = 5L;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private MessageViewConverter underTest;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private Message message;

    @Test
    public void convertDomain() {
        //GIVEN
        given(message.getSender()).willReturn(SENDER_ID);
        given(message.getMessage()).willReturn(MESSAGE);
        given(message.getCreatedAt()).willReturn(CREATED_AT);

        given(characterQueryService.findByCharacterIdValidated(SENDER_ID)).willReturn(character);
        given(character.getCharacterName()).willReturn(CHARACTER_NAME);
        //WHEN
        MessageView result = underTest.convertDomain(message);
        //THEN
        assertThat(result.getSenderId()).isEqualTo(SENDER_ID);
        assertThat(result.getSenderName()).isEqualTo(CHARACTER_NAME);
        assertThat(result.getMessage()).isEqualTo(MESSAGE);
        assertThat(result.getCreatedAt()).isEqualTo(CREATED_AT);
    }
}