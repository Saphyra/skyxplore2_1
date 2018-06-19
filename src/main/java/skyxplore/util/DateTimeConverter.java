package skyxplore.util;

import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class DateTimeConverter extends ConverterBase<Long, LocalDateTime> {
    @Override
    public LocalDateTime convertEntity(Long entity) {
        return LocalDateTime.ofEpochSecond(entity, 0, ZoneOffset.UTC);
    }

    @Override
    public Long convertDomain(LocalDateTime domain) {
        return domain.toEpochSecond(ZoneOffset.UTC);
    }
}
