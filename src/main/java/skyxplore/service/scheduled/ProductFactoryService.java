package skyxplore.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skyxplore.domain.product.Product;
import skyxplore.service.CharacterFacade;
import skyxplore.service.FactoryFacade;
import skyxplore.service.ProductFacade;

import java.util.List;

@SuppressWarnings("unused")
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class ProductFactoryService {
    private final ProductFacade productFacade;
    private final FactoryFacade factoryFacade;
    private final CharacterFacade characterFacade;

    @Scheduled(fixedDelay = 10500L)
    public void process() {
        processFinishedProducts();
        startNextProduct();
    }

    private void processFinishedProducts() {
        log.info("Processing finished products...");
        List<Product> products = productFacade.getFinishedProducts();
        log.info("Number of finished products: {}", products.size());
        products.forEach(this::finishProduct);
    }

    private void finishProduct(Product product) {
        try {
            productFacade.finishProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startNextProduct() {
        log.info("Starting the next product in the queue");
        List<Product> products = productFacade.getFirstOfQueue();
        log.info("Items to start: {}", products.size());
        products.forEach(this::startNextProduct);
    }

    private void startNextProduct(Product product) {
        try {
            productFacade.startBuilding(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
