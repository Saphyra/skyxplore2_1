package com.github.saphyra.skyxplore.product.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductFactoryServiceTest {
    @Mock
    private FinishProductService finishProductService;

    @Mock
    private StartProductService startProductService;

    @InjectMocks
    private ProductFactoryService underTest;

    @Test
    public void process() {
        //WHEN
        underTest.process();
        //THEN
        verify(startProductService).startProducts();
        verify(finishProductService).finishProducts();
    }
}
