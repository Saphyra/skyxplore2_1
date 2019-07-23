package com.github.saphyra.skyxplore.userdata.community.mail.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.userdata.community.mail.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.encryption.impl.StringEncryptor;

@RunWith(MockitoJUnitRunner.class)
public class MailConverterTest {
    private static final String ENCRYPTED_SUBJECT = "encrypted_subject";
    private static final String MAIL_ID = "mail_id";
    private static final String SUBJECT = "subject";
    private static final String ENCRYPTED_MESSAGE = "encrypted_message";
    private static final String MESSAGE = "message";
    private static final Long SEND_TIME_EPOCH = 946L;
    private static final OffsetDateTime SEND_TIME = OffsetDateTime.now(ZoneOffset.UTC);
    private static final String FROM_ID = "from_id";
    private static final String TO_ID = "to_id";
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
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert() {
        //GIVEN
        MailEntity entity = MailEntity.builder()
            .mailId(MAIL_ID)
            .from(FROM_ID)
            .to(TO_ID)
            .subject(ENCRYPTED_SUBJECT)
            .message(ENCRYPTED_MESSAGE)
            .sendTime(SEND_TIME_EPOCH)
            .read(false)
            .archived(false)
            .deletedBySender(false)
            .deletedByAddressee(false)
            .build();

        when(stringEncryptor.decryptEntity(ENCRYPTED_SUBJECT, MAIL_ID)).thenReturn(SUBJECT);
        when(stringEncryptor.decryptEntity(ENCRYPTED_MESSAGE, MAIL_ID)).thenReturn(MESSAGE);
        when(dateTimeUtil.convertEntity(SEND_TIME_EPOCH)).thenReturn(SEND_TIME);
        //WHEN
        Mail result = underTest.convertEntity(entity);
        //THEN
        assertThat(result.getMailId()).isEqualTo(MAIL_ID);
        assertThat(result.getFrom()).isEqualTo(FROM_ID);
        assertThat(result.getTo()).isEqualTo(TO_ID);
        assertThat(result.getSubject()).isEqualTo(SUBJECT);
        assertThat(result.getMessage()).isEqualTo(MESSAGE);
        assertThat(result.getRead()).isFalse();
        assertThat(result.getSendTime()).isEqualTo(SEND_TIME);
        assertThat(result.getArchived()).isFalse();
        assertThat(result.getDeletedBySender()).isFalse();
        assertThat(result.getDeletedByAddressee()).isFalse();
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert() {
        //GIVEN
        Mail mail = Mail.builder()
            .mailId(MAIL_ID)
            .from(FROM_ID)
            .to(TO_ID)
            .subject(SUBJECT)
            .message(MESSAGE)
            .sendTime(SEND_TIME)
            .read(false)
            .archived(false)
            .deletedBySender(false)
            .deletedByAddressee(false)
            .build();

        when(stringEncryptor.encryptEntity(SUBJECT, MAIL_ID)).thenReturn(ENCRYPTED_SUBJECT);
        when(stringEncryptor.encryptEntity(MESSAGE, MAIL_ID)).thenReturn(ENCRYPTED_MESSAGE);
        when(dateTimeUtil.convertDomain(SEND_TIME)).thenReturn(SEND_TIME_EPOCH);
        //WHEN
        MailEntity result = underTest.convertDomain(mail);
        //THEN
        assertThat(result.getMailId()).isEqualTo(MAIL_ID);
        assertThat(result.getFrom()).isEqualTo(FROM_ID);
        assertThat(result.getTo()).isEqualTo(TO_ID);
        assertThat(result.getSubject()).isEqualTo(ENCRYPTED_SUBJECT);
        assertThat(result.getMessage()).isEqualTo(ENCRYPTED_MESSAGE);
        assertThat(result.getRead()).isFalse();
        assertThat(result.getSendTime()).isEqualTo(SEND_TIME_EPOCH);
        assertThat(result.getArchived()).isFalse();
        assertThat(result.getDeletedBySender()).isFalse();
        assertThat(result.getDeletedByAddressee()).isFalse();
    }
}
