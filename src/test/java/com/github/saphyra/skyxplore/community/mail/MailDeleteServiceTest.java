package com.github.saphyra.skyxplore.community.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.exception.InvalidMailAccessException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.community.mail.repository.MailDao;
import com.github.saphyra.skyxplore.community.mail.domain.Mail;

@RunWith(MockitoJUnitRunner.class)
public class MailDeleteServiceTest {
    private static final String MAIL_ID = "mail_id";
    private static final List<String> MAIL_IDS = Arrays.asList(MAIL_ID);
    private static final String CHARACTER_ID = "character_id";
    private static final String TO_ID = "mail_to_id";
    private static final String FROM_ID = "mail_from_id";

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private MailDao mailDao;

    @Mock
    private MailQueryService mailQueryService;

    private Mail mail;

    @InjectMocks
    private MailDeleteService underTest;

    @Before
    public void init() {
        mail = Mail.builder()
            .from(FROM_ID)
            .to(TO_ID)
            .build();
        when(mailQueryService.findMailById(anyString())).thenReturn(mail);
    }

    @After
    public void verifyInteractions() {
        verify(characterQueryService).findByCharacterId(anyString());
        verify(mailQueryService).findMailById(anyString());
    }

    @Test(expected = InvalidMailAccessException.class)
    public void testDeleteMailShouldThrowExceptionWhenWrongId() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterId(CHARACTER_ID)
            .build();
        when(characterQueryService.findByCharacterId(anyString())).thenReturn(character);
        //WHEN
        underTest.deleteMails(CHARACTER_ID, MAIL_IDS);
    }

    @Test
    public void testDeleteMailShouldUpdateDeletedByAddressee() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterId(TO_ID)
            .build();
        when(characterQueryService.findByCharacterId(anyString())).thenReturn(character);
        //WHEN
        underTest.deleteMails(TO_ID, MAIL_IDS);
        //THEN
        assertThat(mail.getDeletedByAddressee()).isTrue();
        verify(mailDao).save(mail);
    }

    @Test
    public void testDeleteMailShouldUpdateDeletedBySender() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterId(FROM_ID)
            .build();
        when(characterQueryService.findByCharacterId(anyString())).thenReturn(character);
        //WHEN
        underTest.deleteMails(FROM_ID, MAIL_IDS);
        //THEN
        assertThat(mail.getDeletedBySender()).isTrue();
        verify(mailDao).save(mail);
    }
}