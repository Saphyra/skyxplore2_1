package com.github.saphyra.skyxplore.community.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.community.mail.repository.MailDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MailCleanupServiceTest {
    private static final OffsetDateTime SEND_TIME = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Long SEND_TIME_EPOCH = 876897L;
    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private MailDao mailDao;

    @InjectMocks
    private MailCleanupService underTest;

    @Test
    public void testDeleteExpiredMails() {
        //GIVEN
        when(dateTimeUtil.now()).thenReturn(SEND_TIME);
        when(dateTimeUtil.convertDomain(any(OffsetDateTime.class))).thenReturn(SEND_TIME_EPOCH);
        //WHEN
        underTest.deleteExpiredMails();
        //THEN
        verify(mailDao).deleteBothSideDeleted();
        verify(mailDao).deleteExpired(SEND_TIME_EPOCH);
    }
}