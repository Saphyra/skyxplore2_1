package skyxplore.service.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.MailNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class MailQueryServiceTest {
    @Mock
    private MailDao mailDao;

    @InjectMocks
    private MailQueryService underTest;

    @Test(expected = MailNotFoundException.class)
    public void testFindByMailIdShouldThrowException() {
        //GIVEN
        when(mailDao.findById(MAIL_ID_1)).thenReturn(Optional.empty());
        //WHEN
        underTest.findMailById(MAIL_ID_1);
    }

    @Test
    public void testFindByMailId() {
        //GIVEN
        Mail mail = createMail();
        when(mailDao.findById(MAIL_ID_1)).thenReturn(Optional.of(mail));
        //WHEN
        Mail result = underTest.findMailById(MAIL_ID_1);
        //THEN
        verify(mailDao).findById(MAIL_ID_1);
        assertEquals(mail, result);
    }

    @Test
    public void testGetArchivedMails() {
        //GIVEN
        Mail mail = createMail();
        when(mailDao.getArchivedMails(CHARACTER_ID_1)).thenReturn(Arrays.asList(mail));
        //WHEN
        List<Mail> result = underTest.getArchivedMails(CHARACTER_ID_1);
        //THEN
        verify(mailDao).getArchivedMails(CHARACTER_ID_1);
        assertEquals(mail, result.get(0));
    }

    @Test
    public void testGetMails() {
        //GIVEN
        Mail mail = createMail();
        when(mailDao.getMails(CHARACTER_ID_1)).thenReturn(Arrays.asList(mail));
        //WHEN
        List<Mail> result = underTest.getMails(CHARACTER_ID_1);
        //THEN
        verify(mailDao).getMails(CHARACTER_ID_1);
        assertEquals(mail, result.get(0));
    }

    @Test
    public void testGetNumberOfUnreadMails() {
        //GIVEN
        Mail mail = createMail();
        when(mailDao.getUnreadMails(CHARACTER_ID_1)).thenReturn(Arrays.asList(mail));
        //WHEN
        Integer result = underTest.getNumberOfUnreadMails(CHARACTER_ID_1);
        //THEN
        verify(mailDao).getUnreadMails(CHARACTER_ID_1);
        assertEquals((Integer) 1, result);
    }

    @Test
    public void testGetSentMails() {
        //GIVEN
        Mail mail = createMail();
        when(mailDao.getSentMails(CHARACTER_ID_1)).thenReturn(Arrays.asList(mail));
        //WHEN
        List<Mail> result = underTest.getSentMails(CHARACTER_ID_1);
        //THEN
        verify(mailDao).getSentMails(CHARACTER_ID_1);
        assertEquals(mail, result.get(0));
    }
}