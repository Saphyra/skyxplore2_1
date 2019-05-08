package com.github.saphyra.skyxplore.community.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.community.mail.domain.MailView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MailViewConverterTest {
    private static final String FROM_NAME = "from_name";
    private static final String TO_NAME = "to_name";
    private static final String FROM_ID = "from_id";
    private static final String TO_ID = "to_id";
    private static final OffsetDateTime SEND_TIME = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Long SEND_TIME_EPOCH = 545L;
    private static final String MAIL_ID = "mail_id";
    private static final String SUBJECT = "subject";
    private static final String MESSAGE = "message";

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private MailViewConverter underTest;

    @Test
    public void testConvertDomainShouldConvertAndReturn() {
        //GIVEN
        Mail mail = Mail.builder()
            .mailId(MAIL_ID)
            .subject(SUBJECT)
            .message(MESSAGE)
            .sendTime(SEND_TIME)
            .to(TO_ID)
            .from(FROM_ID)
            .read(false)
            .build();

        SkyXpCharacter from = SkyXpCharacter.builder().characterName(FROM_NAME).build();

        SkyXpCharacter to = SkyXpCharacter.builder().characterName(TO_NAME).build();

        when(characterQueryService.findByCharacterId(FROM_ID)).thenReturn(from);
        when(characterQueryService.findByCharacterId(TO_ID)).thenReturn(to);
        when(dateTimeUtil.convertDomain(SEND_TIME)).thenReturn(SEND_TIME_EPOCH);
        //WHEN
        MailView result = underTest.convertDomain(mail);
        //THEN
        verify(characterQueryService).findByCharacterId(FROM_ID);
        verify(characterQueryService).findByCharacterId(TO_ID);
        verify(dateTimeUtil).convertDomain(SEND_TIME);

        assertThat(result.getMailId()).isEqualTo(MAIL_ID);
        assertThat(result.getFrom()).isEqualTo(FROM_ID);
        assertThat(result.getTo()).isEqualTo(TO_ID);
        assertThat(result.getFromName()).isEqualTo(FROM_NAME);
        assertThat(result.getToName()).isEqualTo(TO_NAME);
        assertThat(result.getSubject()).isEqualTo(SUBJECT);
        assertThat(result.getMessage()).isEqualTo(MESSAGE);
        assertThat(result.getSendTime()).isEqualTo(SEND_TIME_EPOCH);
        assertThat(result.getRead()).isFalse();
    }
}
