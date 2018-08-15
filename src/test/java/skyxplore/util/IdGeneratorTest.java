package skyxplore.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class IdGeneratorTest {
    @InjectMocks
    private IdGenerator underTest;

    @Test
    public void testGetRandomIdShouldNotReturnNull(){
        //WHEN
        String result = underTest.getRandomId();
        //THEN
        assertNotNull(result);
    }
}
