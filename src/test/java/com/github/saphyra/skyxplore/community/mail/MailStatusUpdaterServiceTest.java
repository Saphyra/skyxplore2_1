package com.github.saphyra.skyxplore.community.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.exception.InvalidMailAccessException;
import com.github.saphyra.skyxplore.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.community.mail.repository.MailDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MailStatusUpdaterServiceTest {
    private static final String MAIL_ID = "mail_id";
    private static final List<String> MAIL_IDS = Arrays.asList(MAIL_ID);
    private static final String CHARACTER_ID = "character_id";
    private static final String TO_ID = "to_id";

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private MailDao mailDao;

    @Mock
    private MailQueryService mailQueryService;

    @InjectMocks
    private MailStatusUpdaterService underTest;

    @Test(expected = InvalidMailAccessException.class)
    public void testArchiveMailsShouldThrowExceptionWhenWrongId() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().characterId(CHARACTER_ID).build();
        when(characterQueryService.findByCharacterId(CHARACTER_ID)).thenReturn(character);

        Mail mail = Mail.builder().to(TO_ID).build();
        when(mailQueryService.findMailById(MAIL_ID)).thenReturn(mail);
        //WHEN
        underTest.archiveMails(CHARACTER_ID, MAIL_IDS, true);
    }

    @Test
    public void testArchiveMailsShouldUpdate() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().characterId(TO_ID).build();
        when(characterQueryService.findByCharacterId(TO_ID)).thenReturn(character);

        Mail mail = Mail.builder().to(TO_ID).build();
        when(mailQueryService.findMailById(MAIL_ID)).thenReturn(mail);
        //WHEN
        underTest.archiveMails(TO_ID, MAIL_IDS, true);
        //THEN
        verify(characterQueryService).findByCharacterId(TO_ID);
        verify(mailQueryService).findMailById(MAIL_ID);
        verify(mailDao).save(mail);
        assertThat(mail.getArchived()).isTrue();
    }

    @Test(expected = InvalidMailAccessException.class)
    public void testUpdateReadStatusShouldThrowExceptionWhenWrongId() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().characterId(CHARACTER_ID).build();
        when(characterQueryService.findByCharacterId(CHARACTER_ID)).thenReturn(character);

        Mail mail = Mail.builder().to(TO_ID).build();
        when(mailQueryService.findMailById(MAIL_ID)).thenReturn(mail);
        //WHEN
        underTest.updateReadStatus(MAIL_IDS, CHARACTER_ID, true);
    }

    @Test
    public void testUpdateReadStatusShouldUpdate() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().characterId(TO_ID).build();
        when(characterQueryService.findByCharacterId(TO_ID)).thenReturn(character);

        Mail mail = Mail.builder().to(TO_ID).build();
        when(mailQueryService.findMailById(MAIL_ID)).thenReturn(mail);
        //WHEN
        underTest.updateReadStatus(MAIL_IDS, TO_ID, true);
        //THEN
        verify(characterQueryService).findByCharacterId(TO_ID);
        verify(mailQueryService).findMailById(MAIL_ID);
        verify(mailDao).save(mail);
        assertThat(mail.getRead()).isTrue();
    }
}