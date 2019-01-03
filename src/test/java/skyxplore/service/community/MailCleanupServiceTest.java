package skyxplore.service.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.util.DateTimeUtil;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.MAIL_SEND_TIME;
import static skyxplore.testutil.TestUtils.MAIL_SEND_TIME_EPOCH;

@RunWith(MockitoJUnitRunner.class)
public class MailCleanupServiceTest {
    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private MailDao mailDao;

    @InjectMocks
    private MailCleanupService underTest;

    @Test
    public void testDeleteExpiredMails(){
        //GIVEN
        when(dateTimeUtil.now()).thenReturn(MAIL_SEND_TIME);
        when(dateTimeUtil.convertDomain(any(OffsetDateTime.class))).thenReturn(MAIL_SEND_TIME_EPOCH);
        //WHEN
        underTest.deleteExpiredMails();
        //THEN
        verify(mailDao).deleteBothSideDeleted();
        verify(mailDao).deleteExpired(MAIL_SEND_TIME_EPOCH);
    }
}