package skyxplore.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skyxplore.domain.product.Product;
import skyxplore.service.CharacterService;
import skyxplore.service.FactoryService;
import skyxplore.service.ProductService;

import java.util.List;

@SuppressWarnings("unused")
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class FinishProducts {
    private final ProductService productService;
    private final FactoryService factoryService;
    private final CharacterService characterService;

    @Scheduled(fixedDelay = 10000L)
    public void process(){
        log.info("Processing finished products...");

        processFinishedProducts();
    }

    private void processFinishedProducts(){
        List<Product> products = productService.getFinishedProducts();
        log.info("Number of finished products: {}", products.size());
        products.forEach(this::finishProduct);
    }

    private void finishProduct(Product product){
        try{
            productService.finishProduct(product);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
