package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FactoryRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class FactoryDao {
    private final FactoryRepository factoryRepository;
}
