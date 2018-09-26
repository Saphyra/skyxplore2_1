package skyxplore.service.community;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.service.character.CharacterQueryService;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MailStatusUpdaterServiceTest {
    @Mock
    private  CharacterQueryService characterQueryService;

    @Mock
    private  MailDao mailDao;

    @Mock
    private  MailQueryService mailQueryService;

    @InjectMocks
    private MailStatusUpdaterService underTest;


}