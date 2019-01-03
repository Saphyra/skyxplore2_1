package skyxplore.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static skyxplore.testutil.TestUtils.PRODUCT_END_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_END_TIME_EPOCH;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeUtilTest {
    private OffsetDateTime now;

    @InjectMocks
    private DateTimeUtil underTest;

    @Before
    public void setUp(){
        now = OffsetDateTime.now(ZoneOffset.UTC);
    }

    @Test
    public void testConvertEntityShouldReturnDomain(){
        //WHEN
        OffsetDateTime result = underTest.convertEntity(PRODUCT_END_TIME_EPOCH);
        //THEN
        assertEquals(PRODUCT_END_TIME, result);
    }

    @Test
    public void testConvertEntityShouldReturnNull(){
        //GIVEN
        Long entity = null;
        //WHEN
        OffsetDateTime result = underTest.convertEntity(entity);
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
        OffsetDateTime domain = null;
        //WHEN
        Long result = underTest.convertDomain(domain);
        //THEN
        assertNull(result);
    }

    @Test
    public void testGetExpirationDateShouldReturn(){
        //WHEN
        OffsetDateTime result = underTest.getExpirationDate();
        //THEN
        assertTrue(result.isBefore(now));
    }
}
