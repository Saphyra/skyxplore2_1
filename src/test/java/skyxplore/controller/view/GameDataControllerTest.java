package skyxplore.controller.view;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.GameDataController;
import skyxplore.controller.request.character.EquipmentCategoryRequest;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.CategoryService;
import skyxplore.service.GameDataFacade;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CATEGORY_CONTENT;
import static skyxplore.testutil.TestUtils.CATEGORY_ID;
import static skyxplore.testutil.TestUtils.createGeneralDescriptionMap;

@RunWith(MockitoJUnitRunner.class)
public class GameDataControllerTest {
    @Mock
    private CategoryService categoryService;

    @Mock
    private GameDataFacade gameDataFacade;

    @InjectMocks
    private GameDataController underTest;

    @Test(expected = NotFoundException.class)
    public void testGetCategoriesShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(categoryService.get(CATEGORY_ID + "_categories")).thenReturn(null);
        //WHEN
        underTest.getCategories(CATEGORY_ID);
    }

    @Test
    public void testGetCategoriesShouldReturnResult() {
        //GIVEN
        when(categoryService.get(CATEGORY_ID + "_categories")).thenReturn(CATEGORY_CONTENT);
        //WHEN
        String result = underTest.getCategories(CATEGORY_ID);
        //THEN
        assertEquals(CATEGORY_CONTENT, result);
    }

    @Test
    public void testGetElementsOfCategoryShouldReturnResult() {
        //GIVEN
        Map<String, GeneralDescription> generalDescription = createGeneralDescriptionMap();
        when(gameDataFacade.getElementsOfCategory(any(EquipmentCategoryRequest.class))).thenReturn(generalDescription);
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.ALL.name());
        //THEN
        verify(gameDataFacade).getElementsOfCategory(any(EquipmentCategoryRequest.class));
        assertEquals(generalDescription, result);
    }
}
