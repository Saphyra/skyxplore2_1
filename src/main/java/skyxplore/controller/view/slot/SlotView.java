package skyxplore.controller.view.slot;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class SlotView {
    private Integer frontSlot;
    private ArrayList<String> frontEquipped;

    private Integer leftSlot;
    private ArrayList<String> leftEquipped;

    private Integer rightSlot;
    private ArrayList<String> rightEquipped;

    private Integer backSlot;
    private ArrayList<String> backEquipped;
}
