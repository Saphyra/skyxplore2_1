package skyxplore.controller;



import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import com.google.common.cache.Cache;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.service.CharacterFacade;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String USER_ID = "user_id";


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
        //GIVEN
        HashMap<String, Integer> inputMap = new HashMap<>();
        //WHEN
        underTest.buyEquipments(inputMap, CHARACTER_ID, USER_ID);
        //THEN
        Mockito.verify(characterFacade).buyItems(inputMap, CHARACTER_ID, USER_ID);
    }
}
