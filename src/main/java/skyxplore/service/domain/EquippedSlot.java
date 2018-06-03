package skyxplore.service.domain;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EquippedSlot {
    private Long slotId;
    private Integer frontSlot;
    private ArrayList<String> frontEquipped = new ArrayList<>();
    private Integer leftSlot;
    private ArrayList<String> leftEquipped = new ArrayList<>();
    private Integer rightSlot;
    private ArrayList<String> rightEquipped = new ArrayList<>();
    private Integer backSlot;
    private ArrayList<String> backEquipped = new ArrayList<>();

    public void addFront(String element) {
        if (frontSlot.equals(frontEquipped.size())) {
            throw new IllegalStateException("Front slot is full.");
        }
        frontEquipped.add(element);
    }

    public void addLeft(String element) {
        if (leftSlot.equals(leftEquipped.size())) {
            throw new IllegalStateException("Left slot is full.");
        }
        leftEquipped.add(element);
    }

    public void addRight(String element) {
        if (rightSlot.equals(rightEquipped.size())) {
            throw new IllegalStateException("Right slot is full.");
        }
        rightEquipped.add(element);
    }

    public void addBack(String element){
        if(backSlot.equals(backEquipped.size())){
            throw new IllegalStateException("Back slot is full.");
        }
        backEquipped.add(element);
    }
}
