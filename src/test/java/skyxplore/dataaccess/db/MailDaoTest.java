package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.MailRepository;
import skyxplore.domain.community.mail.Mail;
import skyxplore.domain.community.mail.MailConverter;
import skyxplore.domain.community.mail.MailEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.MAIL_ID_1;
import static skyxplore.testutil.TestUtils.MAIL_SEND_TIME_EPOCH;
import static skyxplore.testutil.TestUtils.createMail;
import static skyxplore.testutil.TestUtils.createMailEntity;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class MailDaoTest {
    @Mock
    private MailConverter mailConverter;

    @Mock
    private MailRepository mailRepository;

    @InjectMocks
    private MailDao underTest;

    @Test
    public void testDeleteBothSideDeletedShouldCallRepository() {
        //WHEN
        underTest.deleteBothSideDeleted();
        //THEN
        verify(mailRepository).deleteBothSideDeleted();
    }

    @Test
    public void testDeleteByCharacterIdShouldCallRepository() {
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID_1);
        //THEN
        verify(mailRepository).deleteByCharacterId(CHARACTER_ID_1);
    }

    @Test
    public void testDeleteExpiredShouldCallRepository() {
        //WHEN
        underTest.deleteExpired(MAIL_SEND_TIME_EPOCH);
        //THEN
        verify(mailRepository).deleteExpired(MAIL_SEND_TIME_EPOCH);
    }

    @Test
    public void testFindByIdShouldReturnNull() {
        //GIVEN
        when(mailRepository.findById(MAIL_ID_1)).thenReturn(Optional.empty());
        //WHEN
        Optional<Mail> result = underTest.findById(MAIL_ID_1);
        //THEN
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindByIdShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = createMailEntity();
        when(mailRepository.findById(MAIL_ID_1)).thenReturn(Optional.of(entity));

        Mail mail = createMail();
        when(mailConverter.convertEntity(entity)).thenReturn(mail);
        //WHEN
        Optional<Mail> result = underTest.findById(MAIL_ID_1);
        //THEN
        verify(mailRepository).findById(MAIL_ID_1);
        verify(mailConverter).convertEntity(entity);
        assertTrue(result.isPresent());
        assertEquals(mail, result.get());
    }

    @Test
    public void testGetArchivedMailsShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = createMailEntity();
        List<MailEntity> entityList = Arrays.asList(entity);
        when(mailRepository.getArchivedMails(CHARACTER_ID_1)).thenReturn(entityList);

        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailConverter.convertEntity(entityList)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getArchivedMails(CHARACTER_ID_1);
        //THEN
        verify(mailRepository).getArchivedMails(CHARACTER_ID_1);
        verify(mailConverter).convertEntity(entityList);
        assertEquals(mailList, result);
    }

    @Test
    public void testGetMailsShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = createMailEntity();
        List<MailEntity> entityList = Arrays.asList(entity);
        when(mailRepository.getMails(CHARACTER_ID_1)).thenReturn(entityList);

        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailConverter.convertEntity(entityList)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getMails(CHARACTER_ID_1);
        //THEN
        verify(mailRepository).getMails(CHARACTER_ID_1);
        verify(mailConverter).convertEntity(entityList);
        assertEquals(mailList, result);
    }

    @Test
    public void testGetSentMailsShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = createMailEntity();
        List<MailEntity> entityList = Arrays.asList(entity);
        when(mailRepository.getSentMails(CHARACTER_ID_1)).thenReturn(entityList);

        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailConverter.convertEntity(entityList)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getSentMails(CHARACTER_ID_1);
        //THEN
        verify(mailRepository).getSentMails(CHARACTER_ID_1);
        verify(mailConverter).convertEntity(entityList);
        assertEquals(mailList, result);
    }

    @Test
    public void testGetUnreadMailsShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = createMailEntity();
        List<MailEntity> entityList = Arrays.asList(entity);
        when(mailRepository.getUnreadMails(CHARACTER_ID_1)).thenReturn(entityList);

        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailConverter.convertEntity(entityList)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getUnreadMails(CHARACTER_ID_1);
        //THEN
        verify(mailRepository).getUnreadMails(CHARACTER_ID_1);
        verify(mailConverter).convertEntity(entityList);
        assertEquals(mailList, result);
    }

    @Test
    public void testSaveShouldCallRepository() {
        //GIVEN
        Mail mail = createMail();
        MailEntity entity = createMailEntity();
        when(mailConverter.convertDomain(mail)).thenReturn(entity);
        //WHEN
        underTest.save(mail);
        //THEN
        verify(mailConverter).convertDomain(mail);
        verify(mailRepository).save(entity);
    }
}
