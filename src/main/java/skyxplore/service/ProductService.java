package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.ProductDao;

@SuppressWarnings("unused")
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductDao productDao;
}
