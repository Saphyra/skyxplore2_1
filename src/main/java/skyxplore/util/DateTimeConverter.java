package skyxplore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Slf4j
public class DateTimeConverter extends ConverterBase<Long, LocalDateTime> {
    @Override
    public LocalDateTime convertEntity(Long entity) {
        if(entity == null){
            return null;
        }
        return LocalDateTime.ofEpochSecond(entity, 0, ZoneOffset.UTC);
    }

    @Override
    public Long convertDomain(LocalDateTime domain) {
        if(domain == null){
            return null;
        }
        return domain.toEpochSecond(ZoneOffset.UTC);
    }

    public LocalDateTime now(){
        return LocalDateTime.now(ZoneOffset.UTC).withNano(0);
    }
}
