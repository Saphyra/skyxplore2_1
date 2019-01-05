package selenium.aaold.cases.equipment.testcase.helper;

import static org.junit.Assert.assertEquals;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EquipmentVerifier {
    private final ElementSearcher elementSearcher;

    public void verifyItemInStorage(String itemId, int amount){
        assertEquals(amount, elementSearcher.getUnequippedEquipmentById(itemId).getAmount());
    }
}
