package com.github.saphyra.skyxplore.community.mail.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.event.CharacterDeletedEvent;

@RunWith(MockitoJUnitRunner.class)
public class MailDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final Long SEND_TIME_EPOCH = 2423L;
    private static final String MAIL_ID = "mail_id";

    @Mock
    private MailConverter mailConverter;

    @Mock
    private MailRepository mailRepository;

    @Mock
    private Mail mail;

    @Mock
    private MailEntity mailEntity;

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
        when(mailRepository.findById(MAIL_ID)).thenReturn(Optional.of(mailEntity));

        when(mailConverter.convertEntity(mailEntity)).thenReturn(mail);
        //WHEN
        Optional<Mail> result = underTest.findById(MAIL_ID);
        //THEN
        verify(mailRepository).findById(MAIL_ID);
        verify(mailConverter).convertEntity(mailEntity);
        assertThat(result).contains(mail);
    }

    @Test
    public void testGetArchivedMailsShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        List<MailEntity> entityList = Arrays.asList(mailEntity);
        when(mailRepository.getArchivedMails(CHARACTER_ID)).thenReturn(entityList);

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
        List<MailEntity> entityList = Arrays.asList(mailEntity);
        when(mailRepository.getMails(CHARACTER_ID)).thenReturn(entityList);

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
        List<MailEntity> entityList = Arrays.asList(mailEntity);
        when(mailRepository.getSentMails(CHARACTER_ID)).thenReturn(entityList);

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
        List<MailEntity> entityList = Arrays.asList(mailEntity);
        when(mailRepository.getUnreadMails(CHARACTER_ID)).thenReturn(entityList);

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
        when(mailConverter.convertDomain(mail)).thenReturn(mailEntity);
        //WHEN
        underTest.save(mail);
        //THEN
        verify(mailConverter).convertDomain(mail);
        verify(mailRepository).save(mailEntity);
    }
}
