package skyxplore.domain.community.mail;

import com.github.saphyra.encryption.impl.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.util.DateTimeUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.MAIL_ENCRYPTED_MESSAGE;
import static skyxplore.testutil.TestUtils.MAIL_ENCRYPTED_SUBJECT;
import static skyxplore.testutil.TestUtils.MAIL_FROM_ID;
import static skyxplore.testutil.TestUtils.MAIL_ID_1;
import static skyxplore.testutil.TestUtils.MAIL_MESSAGE;
import static skyxplore.testutil.TestUtils.MAIL_SEND_TIME;
import static skyxplore.testutil.TestUtils.MAIL_SEND_TIME_EPOCH;
import static skyxplore.testutil.TestUtils.MAIL_SUBJECT;
import static skyxplore.testutil.TestUtils.MAIL_TO_ID;
import static skyxplore.testutil.TestUtils.createMail;
import static skyxplore.testutil.TestUtils.createMailEntity;

@RunWith(MockitoJUnitRunner.class)
public class MailConverterTest {
    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private MailConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull() {
        //GIVEN
        MailEntity entity = null;
        //WHEN
        Mail result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert() {
        //GIVEN
        MailEntity entity = createMailEntity();

        when(stringEncryptor.decryptEntity(MAIL_ENCRYPTED_SUBJECT, MAIL_ID_1)).thenReturn(MAIL_SUBJECT);
        when(stringEncryptor.decryptEntity(MAIL_ENCRYPTED_MESSAGE, MAIL_ID_1)).thenReturn(MAIL_MESSAGE);
        when(dateTimeUtil.convertEntity(MAIL_SEND_TIME_EPOCH)).thenReturn(MAIL_SEND_TIME);
        //WHEN
        Mail result = underTest.convertEntity(entity);
        //THEN
        assertEquals(MAIL_ID_1, result.getMailId());
        assertEquals(MAIL_FROM_ID, result.getFrom());
        assertEquals(MAIL_TO_ID, result.getTo());
        assertEquals(MAIL_SUBJECT, result.getSubject());
        assertEquals(MAIL_MESSAGE, result.getMessage());
        assertFalse(result.getRead());
        assertEquals(MAIL_SEND_TIME, result.getSendTime());
        assertFalse(result.getArchived());
        assertFalse(result.getDeletedBySender());
        assertFalse(result.getDeletedByAddressee());
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert(){
        //GIVEN
        Mail mail = createMail();

        when(stringEncryptor.encryptEntity(MAIL_SUBJECT, MAIL_ID_1)).thenReturn(MAIL_ENCRYPTED_SUBJECT);
        when(stringEncryptor.encryptEntity(MAIL_MESSAGE, MAIL_ID_1)).thenReturn(MAIL_ENCRYPTED_MESSAGE);
        when(dateTimeUtil.convertDomain(MAIL_SEND_TIME)).thenReturn(MAIL_SEND_TIME_EPOCH);
        //WHEN
        MailEntity result = underTest.convertDomain(mail);
        //THEN
        assertEquals(MAIL_ID_1, result.getMailId());
        assertEquals(MAIL_FROM_ID, result.getFrom());
        assertEquals(MAIL_TO_ID, result.getTo());
        assertEquals(MAIL_ENCRYPTED_SUBJECT, result.getSubject());
        assertEquals(MAIL_ENCRYPTED_MESSAGE, result.getMessage());
        assertFalse(result.getRead());
        assertEquals(MAIL_SEND_TIME_EPOCH, result.getSendTime());
        assertFalse(result.getArchived());
        assertFalse(result.getDeletedBySender());
        assertFalse(result.getDeletedByAddressee());
    }
}
