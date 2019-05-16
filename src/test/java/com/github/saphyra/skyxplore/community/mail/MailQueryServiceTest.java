package com.github.saphyra.skyxplore.community.mail;

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

import com.github.saphyra.skyxplore.common.exception.MailNotFoundException;
import com.github.saphyra.skyxplore.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.community.mail.repository.MailDao;

@RunWith(MockitoJUnitRunner.class)
public class MailQueryServiceTest {
    private static final String MAIL_ID = "mail_id";
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private MailDao mailDao;

    @Mock
    private Mail mail;

    @InjectMocks
    private MailQueryService underTest;

    @Test(expected = MailNotFoundException.class)
    public void testFindByMailIdShouldThrowException() {
        //GIVEN
        when(mailDao.findById(MAIL_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.findMailById(MAIL_ID);
    }

    @Test
    public void testFindByMailId() {
        //GIVEN
        when(mailDao.findById(MAIL_ID)).thenReturn(Optional.of(mail));
        //WHEN
        Mail result = underTest.findMailById(MAIL_ID);
        //THEN
        verify(mailDao).findById(MAIL_ID);
        assertThat(result).isEqualTo(mail);
    }

    @Test
    public void testGetArchivedMails() {
        //GIVEN
        when(mailDao.getArchivedMails(CHARACTER_ID)).thenReturn(Arrays.asList(mail));
        //WHEN
        List<Mail> result = underTest.getArchivedMails(CHARACTER_ID);
        //THEN
        verify(mailDao).getArchivedMails(CHARACTER_ID);
        assertThat(result).containsExactly(mail);
    }

    @Test
    public void testGetMails() {
        //GIVEN
        when(mailDao.getMails(CHARACTER_ID)).thenReturn(Arrays.asList(mail));
        //WHEN
        List<Mail> result = underTest.getMails(CHARACTER_ID);
        //THEN
        verify(mailDao).getMails(CHARACTER_ID);
        assertThat(result).containsExactly(mail);
    }

    @Test
    public void testGetNumberOfUnreadMails() {
        //GIVEN
        when(mailDao.getUnreadMails(CHARACTER_ID)).thenReturn(Arrays.asList(mail));
        //WHEN
        Integer result = underTest.getNumberOfUnreadMails(CHARACTER_ID);
        //THEN
        verify(mailDao).getUnreadMails(CHARACTER_ID);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testGetSentMails() {
        //GIVEN
        when(mailDao.getSentMails(CHARACTER_ID)).thenReturn(Arrays.asList(mail));
        //WHEN
        List<Mail> result = underTest.getSentMails(CHARACTER_ID);
        //THEN
        verify(mailDao).getSentMails(CHARACTER_ID);
        assertThat(result).containsExactly(mail);
    }
}