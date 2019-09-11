package com.github.saphyra.skyxplore.common;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DateTimeUtil extends ConverterBase<Long, OffsetDateTime> {
    @Override
    public OffsetDateTime processEntityConversion(Long entity) {
        if (entity == null) {
            return null;
        }
        return OffsetDateTime.of(LocalDateTime.ofEpochSecond(entity, 0, ZoneOffset.UTC), ZoneOffset.UTC);
    }

    @Override
    public Long processDomainConversion(OffsetDateTime domain) {
        if (domain == null) {
            return null;
        }
        return domain.toEpochSecond();
    }

    public OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC).withNano(0);
    }

    //TODO unit test
    public long nowEpoch() {
        return convertDomain(now());
    }
}
