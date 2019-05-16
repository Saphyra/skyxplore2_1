package com.github.saphyra.skyxplore.community.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import com.github.saphyra.skyxplore.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.community.mail.domain.MailView;
import com.github.saphyra.skyxplore.community.mail.domain.SendMailRequest;

@RunWith(MockitoJUnitRunner.class)
public class MailControllerTest {
    private static final String MAIL_ID = "mail_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";
    @Mock
    private CharacterViewConverter characterViewConverter;

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

    @Mock
    private MailViewConverter mailViewConverter;

    @InjectMocks
    private MailController underTest;

    @Mock
    private Mail mail;

    @Mock
    private MailView mailView;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private CharacterView characterView;

    @Test
    public void testArchiveMailsShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID);
        //WHEN
        underTest.archiveMails(mailIds, CHARACTER_ID);
        //THEN
        verify(mailStatusUpdaterService).archiveMails(CHARACTER_ID, mailIds, true);
    }

    @Test
    public void testDeleteMailsShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID);
        //WHEN
        underTest.deleteMails(mailIds, CHARACTER_ID);
        //THEN
        verify(mailDeleteService).deleteMails(CHARACTER_ID, mailIds);
    }

    @Test
    public void testGetAddresseesShouldCallFacadeAndReturnView() {
        //GIVEN
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(characterQueryService.getCharactersCanBeAddressee(CHARACTER_ID, CHARACTER_NAME)).thenReturn(characterList);

        List<CharacterView> viewList = Arrays.asList(characterView);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getAddressees(new OneStringParamRequest(CHARACTER_NAME), CHARACTER_ID);
        //THEN
        verify(characterViewConverter).convertDomain(characterList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetArchivedMailsShouldCallFacadeAndReturnView() {
        //GIVEN
        List<Mail> mailList = Arrays.asList(mail);
        when(mailQueryService.getArchivedMails(CHARACTER_ID)).thenReturn(mailList);

        List<MailView> viewList = Arrays.asList(mailView);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getArchivedMails(CHARACTER_ID);
        //THEN
        verify(mailViewConverter).convertDomain(mailList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetMailsShouldCallFacadeAndReturnView() {
        //GIVEN
        List<Mail> mailList = Arrays.asList(mail);
        when(mailQueryService.getMails(CHARACTER_ID)).thenReturn(mailList);

        List<MailView> viewList = Arrays.asList(mailView);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getMails(CHARACTER_ID);
        //THEN
        verify(mailViewConverter).convertDomain(mailList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetSentMailsShouldCallFacadeAndReturnView() {
        //GIVEN
        List<Mail> mailList = Arrays.asList(mail);
        when(mailQueryService.getSentMails(CHARACTER_ID)).thenReturn(mailList);

        List<MailView> viewList = Arrays.asList(mailView);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getSentMails(CHARACTER_ID);
        //THEN
        verify(mailViewConverter).convertDomain(mailList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testMarkMailsReadShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID);
        //WHEN
        underTest.markMailsRead(mailIds, CHARACTER_ID);
        //THEN
        verify(mailStatusUpdaterService).updateReadStatus(mailIds, CHARACTER_ID, true);
    }

    @Test
    public void testMarkMailsUnreadShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID);
        //WHEN
        underTest.markMailsUnread(mailIds, CHARACTER_ID);
        //THEN
        verify(mailStatusUpdaterService).updateReadStatus(mailIds, CHARACTER_ID, false);
    }

    @Test
    public void testSendMailShouldCallFacade() {
        //GIVEN
        SendMailRequest request = new SendMailRequest();
        //WHEN
        underTest.sendMail(request, CHARACTER_ID);
        //THEN
        verify(mailSenderService).sendMail(request, CHARACTER_ID);
    }

    @Test
    public void testUnarchiveMailsShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID);
        //WHEN
        underTest.restoreMails(mailIds, CHARACTER_ID);
        //THEN
        verify(mailStatusUpdaterService).archiveMails(CHARACTER_ID, mailIds, false);
    }
}
