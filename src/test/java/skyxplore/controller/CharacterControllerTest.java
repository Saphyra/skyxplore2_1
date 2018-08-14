package skyxplore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.cache.Cache;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.service.CharacterFacade;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {
    @Mock
    private CharacterFacade characterFacade;

    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private Cache<String, Boolean> characterNameCache;

    @InjectMocks
    private CharacterController underTest;

    @Test
    public void testBuyEquipmentsShouldCallFacade(){

    }
}
