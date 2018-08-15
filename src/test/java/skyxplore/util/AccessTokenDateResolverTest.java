package skyxplore.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.util.AccessTokenDateResolver;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenDateResolverTest {
    @InjectMocks
    private AccessTokenDateResolver underTest;

    private LocalDateTime now;

    @Before
    public void setUp(){
        now = underTest.getActualDate();
    }

    @Test
    public void testGetExpirationDateShouldReturn(){
        //WHEN
        LocalDateTime result = underTest.getExpirationDate();
        //THEN
        assertTrue(result.isBefore(now));
    }
}
