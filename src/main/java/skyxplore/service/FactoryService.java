package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.FactoryDao;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryService {
    private final FactoryDao factoryDao;
}
