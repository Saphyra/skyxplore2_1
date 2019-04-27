package org.github.saphyra.skyxplore.common;

import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.junit.Test;

import java.time.OffsetDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static skyxplore.testutil.TestUtils.PRODUCT_END_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_END_TIME_EPOCH;

public class DateTimeUtilTest {
    private DateTimeUtil underTest = new DateTimeUtil();

    @Test
    public void testConvertEntityShouldReturnDomain() {
        //WHEN
        OffsetDateTime result = underTest.convertEntity(PRODUCT_END_TIME_EPOCH);
        //THEN
        assertEquals(PRODUCT_END_TIME, result);
    }

    @Test
    public void testConvertEntityShouldReturnNull() {
        //GIVEN
        Long entity = null;
        //WHEN
        OffsetDateTime result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertDomainShouldReturnEntity() {
        //WHEN
        Long result = underTest.convertDomain(PRODUCT_END_TIME);
        //THEN
        assertEquals(PRODUCT_END_TIME_EPOCH, result);
    }

    @Test
    public void testConvertDomainShouldReturnNull() {
        //GIVEN
        OffsetDateTime domain = null;
        //WHEN
        Long result = underTest.convertDomain(domain);
        //THEN
        assertNull(result);
    }
}
