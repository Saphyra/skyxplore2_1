package org.github.saphyra.skyxplore.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FactoryFacadeTest {
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private FactoryCreatorService factoryCreatorService;

    @InjectMocks
    private FactoryFacade underTest;

    @Test
    public void createFactory() {
        //WHEN
        underTest.createFactory(CHARACTER_ID);
        //THEN
        verify(factoryCreatorService).createFactory(CHARACTER_ID);
    }
}