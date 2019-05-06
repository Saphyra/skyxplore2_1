package org.github.saphyra.skyxplore.community.mail.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.github.saphyra.skyxplore.community.mail.domain.Mail;
import org.github.saphyra.skyxplore.event.CharacterDeletedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MailDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final Long SEND_TIME_EPOCH = 2423L;
    private static final String MAIL_ID = "mail_id";
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
        underTest.characterDeletedEventListener(new CharacterDeletedEvent(CHARACTER_ID));
        //THEN
        verify(mailRepository).deleteByCharacterId(CHARACTER_ID);
    }

    @Test
    public void testDeleteExpiredShouldCallRepository() {
        //WHEN
        underTest.deleteExpired(SEND_TIME_EPOCH);
        //THEN
        verify(mailRepository).deleteExpired(SEND_TIME_EPOCH);
    }

    @Test
    public void testFindByIdShouldReturnNull() {
        //GIVEN
        when(mailRepository.findById(MAIL_ID)).thenReturn(Optional.empty());
        //WHEN
        Optional<Mail> result = underTest.findById(MAIL_ID);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindByIdShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = MailEntity.builder().build();
        when(mailRepository.findById(MAIL_ID)).thenReturn(Optional.of(entity));

        Mail mail = Mail.builder().build();
        when(mailConverter.convertEntity(entity)).thenReturn(mail);
        //WHEN
        Optional<Mail> result = underTest.findById(MAIL_ID);
        //THEN
        verify(mailRepository).findById(MAIL_ID);
        verify(mailConverter).convertEntity(entity);
        assertThat(result).contains(mail);
    }

    @Test
    public void testGetArchivedMailsShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = MailEntity.builder().build();
        List<MailEntity> entityList = Arrays.asList(entity);
        when(mailRepository.getArchivedMails(CHARACTER_ID)).thenReturn(entityList);

        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailConverter.convertEntity(entityList)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getArchivedMails(CHARACTER_ID);
        //THEN
        verify(mailRepository).getArchivedMails(CHARACTER_ID);
        verify(mailConverter).convertEntity(entityList);
        assertThat(result).isEqualTo(mailList);
    }

    @Test
    public void testGetMailsShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = MailEntity.builder().build();
        List<MailEntity> entityList = Arrays.asList(entity);
        when(mailRepository.getMails(CHARACTER_ID)).thenReturn(entityList);

        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailConverter.convertEntity(entityList)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getMails(CHARACTER_ID);
        //THEN
        verify(mailRepository).getMails(CHARACTER_ID);
        verify(mailConverter).convertEntity(entityList);
        assertThat(result).isEqualTo(mailList);
    }

    @Test
    public void testGetSentMailsShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = MailEntity.builder().build();
        List<MailEntity> entityList = Arrays.asList(entity);
        when(mailRepository.getSentMails(CHARACTER_ID)).thenReturn(entityList);

        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailConverter.convertEntity(entityList)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getSentMails(CHARACTER_ID);
        //THEN
        verify(mailRepository).getSentMails(CHARACTER_ID);
        verify(mailConverter).convertEntity(entityList);
        assertThat(result).isEqualTo(mailList);
    }

    @Test
    public void testGetUnreadMailsShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        MailEntity entity = MailEntity.builder().build();
        List<MailEntity> entityList = Arrays.asList(entity);
        when(mailRepository.getUnreadMails(CHARACTER_ID)).thenReturn(entityList);

        Mail mail = Mail.builder().build();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailConverter.convertEntity(entityList)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getUnreadMails(CHARACTER_ID);
        //THEN
        verify(mailRepository).getUnreadMails(CHARACTER_ID);
        verify(mailConverter).convertEntity(entityList);
        assertThat(result).isEqualTo(mailList);
    }

    @Test
    public void testSaveShouldCallRepository() {
        //GIVEN
        Mail mail = Mail.builder().build();
        MailEntity entity = MailEntity.builder().build();
        when(mailConverter.convertDomain(mail)).thenReturn(entity);
        //WHEN
        underTest.save(mail);
        //THEN
        verify(mailConverter).convertDomain(mail);
        verify(mailRepository).save(entity);
    }
}
