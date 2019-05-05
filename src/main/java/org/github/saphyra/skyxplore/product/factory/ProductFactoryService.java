package org.github.saphyra.skyxplore.product.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
//TODO unit test
class ProductFactoryService {
    private final FinishProductService finishProductService;
    private final StartProductService startProductService;

    @Scheduled(fixedDelay = 10500L)
    void process() {
        finishProductService.finishProducts();
        startProductService.startProducts();
    }
}
