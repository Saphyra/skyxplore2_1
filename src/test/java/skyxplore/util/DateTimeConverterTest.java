package skyxplore.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static testutil.TestUtils.PRODUCT_END_TIME;
import static testutil.TestUtils.PRODUCT_END_TIME_EPOCH;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeConverterTest {
    @InjectMocks
    private DateTimeConverter underTest;

    @Test
    public void testConvertEntityShouldReturnDomain(){
        //WHEN
        LocalDateTime result = underTest.convertEntity(PRODUCT_END_TIME_EPOCH);
        //THEN
        assertEquals(PRODUCT_END_TIME, result);
    }

    @Test
    public void testConvertEntityShouldReturnNull(){
        //GIVEN
        Long entity = null;
        //WHEN
        LocalDateTime result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertDomainShouldReturnEntity(){
        //WHEN
        Long result = underTest.convertDomain(PRODUCT_END_TIME);
        //THEN
        assertEquals(PRODUCT_END_TIME_EPOCH, result);
    }

    @Test
    public void testConvertDomainShouldReturnNull(){
        //GIVEN
        LocalDateTime domain = null;
        //WHEN
        Long result = underTest.convertDomain(domain);
        //THEN
        assertNull(result);
    }
}
