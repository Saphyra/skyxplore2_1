package org.github.saphyra.skyxplore.community.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.common.domain.character.CharacterView;
import org.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import org.github.saphyra.skyxplore.common.OneStringParamRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.community.mail.domain.SendMailRequest;
import org.github.saphyra.skyxplore.community.mail.domain.MailView;
import org.github.saphyra.skyxplore.community.mail.domain.Mail;

@RunWith(MockitoJUnitRunner.class)
public class MailControllerTest {
    private static final String MAIL_ID = "mail_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";
    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private MailFacade mailFacade;

    @Mock
    private MailViewConverter mailViewConverter;

    @InjectMocks
    private MailController underTest;

    @Test
    public void testArchiveMailsShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID);
        //WHEN
        underTest.archiveMails(mailIds, CHARACTER_ID);
        //THEN
        verify(mailFacade).archiveMails(CHARACTER_ID, mailIds, true);
    }

    @Test
    public void testDeleteMailsShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID);
        //WHEN
        underTest.deleteMails(mailIds, CHARACTER_ID);
        //THEN
        verify(mailFacade).deleteMails(CHARACTER_ID, mailIds);
    }

    @Test
    public void testGetAddresseesShouldCallFacadeAndReturnView() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(mailFacade.getAddressees(CHARACTER_ID, CHARACTER_NAME)).thenReturn(characterList);

        List<CharacterView> viewList = Arrays.asList(CharacterView.builder().build());
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getAddressees(new OneStringParamRequest(CHARACTER_NAME), CHARACTER_ID);
        //THEN
        verify(mailFacade).getAddressees(CHARACTER_ID, CHARACTER_NAME);
        verify(characterViewConverter).convertDomain(characterList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetArchivedMailsShouldCallFacadeAndReturnView() {
        //GIVEN
        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailFacade.getArchivedMails(CHARACTER_ID)).thenReturn(mailList);

        MailView view = MailView.builder().build();
        List<MailView> viewList = Arrays.asList(view);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getArchivedMails(CHARACTER_ID);
        //THEN
        verify(mailFacade).getArchivedMails(CHARACTER_ID);
        verify(mailViewConverter).convertDomain(mailList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetMailsShouldCallFacadeAndReturnView() {
        //GIVEN
        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailFacade.getMails(CHARACTER_ID)).thenReturn(mailList);

        MailView view = MailView.builder().build();
        List<MailView> viewList = Arrays.asList(view);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getMails(CHARACTER_ID);
        //THEN
        verify(mailFacade).getMails(CHARACTER_ID);
        verify(mailViewConverter).convertDomain(mailList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetSentMailsShouldCallFacadeAndReturnView() {
        //GIVEN
        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailFacade.getSentMails(CHARACTER_ID)).thenReturn(mailList);

        MailView view = MailView.builder().build();
        List<MailView> viewList = Arrays.asList(view);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getSentMails(CHARACTER_ID);
        //THEN
        verify(mailFacade).getSentMails(CHARACTER_ID);
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
        verify(mailFacade).setMailReadStatus(mailIds, CHARACTER_ID, true);
    }

    @Test
    public void testMarkMailsUnreadShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID);
        //WHEN
        underTest.markMailsUnread(mailIds, CHARACTER_ID);
        //THEN
        verify(mailFacade).setMailReadStatus(mailIds, CHARACTER_ID, false);
    }

    @Test
    public void testSendMailShouldCallFacade() {
        //GIVEN
        SendMailRequest request = new SendMailRequest();
        //WHEN
        underTest.sendMail(request, CHARACTER_ID);
        //THEN
        verify(mailFacade).sendMail(request, CHARACTER_ID);
    }

    @Test
    public void testUnarchiveMailsShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID);
        //WHEN
        underTest.restoreMails(mailIds, CHARACTER_ID);
        //THEN
        verify(mailFacade).archiveMails(CHARACTER_ID, mailIds, false);
    }
}
