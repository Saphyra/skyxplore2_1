package skyxplore.restcontroller.view;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SlotView {
    private Long slotId;

    private Integer frontSlot;
    private ArrayList<String> frontEquipped;

    private Integer leftSlot;
    private ArrayList<String> leftEquipped;

    private Integer rightSlot;
    private ArrayList<String> rightEquipped;

    private Integer backSlot;
    private ArrayList<String> backEquipped;
}
