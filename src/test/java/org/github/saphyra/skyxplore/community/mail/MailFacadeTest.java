package org.github.saphyra.skyxplore.community.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.mail.domain.SendMailRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.domain.community.mail.Mail;
import skyxplore.service.community.MailDeleteService;
import skyxplore.service.community.MailQueryService;
import skyxplore.service.community.MailSenderService;
import skyxplore.service.community.MailStatusUpdaterService;

@RunWith(MockitoJUnitRunner.class)
public class MailFacadeTest {
    private static final String MAIL_ID = "mail_id";
    private static final List<String> MAIL_IDS = Arrays.asList(MAIL_ID);
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private MailDeleteService mailDeleteService;

    @Mock
    private MailSenderService mailSenderService;

    @Mock
    private MailStatusUpdaterService mailStatusUpdaterService;

    @Mock
    private MailQueryService mailQueryService;

    @InjectMocks
    private MailFacade underTest;

    @Test
    public void testArchiveMailsShouldCallService() {
        //WHEN
        underTest.archiveMails(CHARACTER_ID, MAIL_IDS, true);
        //THEN
        verify(mailStatusUpdaterService).archiveMails(CHARACTER_ID, MAIL_IDS, true);
    }

    @Test
    public void testDeleteMailsShouldCallService() {
        //WHEN
        underTest.deleteMails(CHARACTER_ID, MAIL_IDS);
        //THEN
        verify(mailDeleteService).deleteMails(CHARACTER_ID, MAIL_IDS);
    }

    @Test
    public void testGetAddresseesShouldCallServiceAndReturn() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(characterQueryService.getCharactersCanBeAddressee(CHARACTER_NAME, CHARACTER_ID)).thenReturn(characterList);
        //WHEN
        List<SkyXpCharacter> result = underTest.getAddressees(CHARACTER_ID, CHARACTER_NAME);
        //THEN
        verify(characterQueryService).getCharactersCanBeAddressee(CHARACTER_NAME, CHARACTER_ID);
        assertThat(result).isEqualTo(characterList);
    }

    @Test
    public void testGetArchivedMails() {
        //GIVEN
        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailQueryService.getArchivedMails(CHARACTER_ID)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getArchivedMails(CHARACTER_ID);
        //THEN
        verify(mailQueryService).getArchivedMails(CHARACTER_ID);
        assertThat(result).isEqualTo(mailList);
    }

    @Test
    public void testGetMails() {
        //GIVEN
        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailQueryService.getMails(CHARACTER_ID)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getMails(CHARACTER_ID);
        //THEN
        verify(mailQueryService).getMails(CHARACTER_ID);
        assertThat(result).isEqualTo(mailList);
    }

    @Test
    public void testGetNumberOfUnreadMails() {
        //GIVEN
        when(mailQueryService.getNumberOfUnreadMails(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfUnreadMails(CHARACTER_ID);
        //THEN
        verify(mailQueryService).getNumberOfUnreadMails(CHARACTER_ID);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testGetSentMails() {
        //GIVEN
        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailQueryService.getSentMails(CHARACTER_ID)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getSentMails(CHARACTER_ID);
        //THEN
        verify(mailQueryService).getSentMails(CHARACTER_ID);
        assertThat(result).isEqualTo(mailList);
    }

    @Test
    public void testSendMail() {
        //GIVEN
        SendMailRequest request = new SendMailRequest();
        //WHEN
        underTest.sendMail(request, CHARACTER_ID);
        //THEN
        verify(mailSenderService).sendMail(request, CHARACTER_ID);
    }

    @Test
    public void testSetMailReadStatus() {
        //WHEN
        underTest.setMailReadStatus(MAIL_IDS, CHARACTER_ID, true);
        //THEN
        verify(mailStatusUpdaterService).updateReadStatus(MAIL_IDS, CHARACTER_ID, true);
    }
}