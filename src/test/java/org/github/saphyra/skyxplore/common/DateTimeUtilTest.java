package org.github.saphyra.skyxplore.common;

import org.junit.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static junit.framework.TestCase.assertNull;
import static org.assertj.core.api.Assertions.assertThat;

public class DateTimeUtilTest {
    private static final OffsetDateTime TIME = OffsetDateTime.now(ZoneOffset.UTC).withNano(0);
    private static final Long TIME_EPOCH = TIME.toEpochSecond();
    private DateTimeUtil underTest = new DateTimeUtil();

    @Test
    public void testConvertEntityShouldReturnDomain() {
        //WHEN
        OffsetDateTime result = underTest.convertEntity(TIME_EPOCH);
        //THEN
        assertThat(result).isEqualTo(TIME);
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
        Long result = underTest.convertDomain(TIME);
        //THEN
        assertThat(result).isEqualTo(TIME_EPOCH);
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
