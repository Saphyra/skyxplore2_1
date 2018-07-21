package skyxplore.domain.slot;

import lombok.Data;
import skyxplore.exception.base.BadRequestException;

import java.util.ArrayList;
import java.util.Collection;

@Data
@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
public class EquippedSlot {
    private String slotId;
    private String shipId;
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
            throw new BadRequestException("Front slot is full.");
        }
        frontEquipped.add(element);
    }

    public void addFrontAll(Collection<String> elements){
        elements.forEach(this::addFront);
    }

    public void removeFront(String element){
        if(!frontEquipped.remove(element)){
            throw new BadRequestException("Front slot does not contain element " + element);
        }
    }

    public void addLeft(String element) {
        if (leftSlot.equals(leftEquipped.size())) {
            throw new BadRequestException("Left slot is full.");
        }
        leftEquipped.add(element);
    }

    public void addLeftAll(Collection<String> elements){
        elements.forEach(this::addLeft);
    }

    public void removeLeft(String element){
        if(!leftEquipped.remove(element)){
            throw new BadRequestException("Left slot does not contain element " + element);
        }
    }

    public void addRight(String element) {
        if (rightSlot.equals(rightEquipped.size())) {
            throw new BadRequestException("Right slot is full.");
        }
        rightEquipped.add(element);
    }

    public void addRightAll(Collection<String> elements){
        elements.forEach(this::addRight);
    }

    public void removeRight(String element){
        if(!rightEquipped.remove(element)){
            throw new BadRequestException("Right slot does not contain element " + element);
        }
    }

    public void addBack(String element){
        if(backSlot.equals(backEquipped.size())){
            throw new BadRequestException("Back slot is full.");
        }
        backEquipped.add(element);
    }

    public void addBackAll(Collection<String> elements){
        elements.forEach(this::addBack);
    }

    public void removeBack(String element){
        if(!backEquipped.remove(element)){
            throw new BadRequestException("Back slot does not contain element " + element);
        }
    }

    public ArrayList<String> getFrontEquipped(){
        return (ArrayList<String>) frontEquipped.clone();
    }

    private void setFrontEquipped(ArrayList<String> s){
        throw new UnsupportedOperationException("EquippedSlot cannot be set.");
    }

    public ArrayList<String> getBackEquipped(){
        return (ArrayList<String>) backEquipped.clone();
    }

    private void setBackEquipped(ArrayList<String> s){
        throw new UnsupportedOperationException("EquippedSlot cannot be set.");
    }

    public ArrayList<String> getLeftEquipped(){
        return (ArrayList<String>) leftEquipped.clone();
    }

    private void setLeftEquipped(ArrayList<String> s){
        throw new UnsupportedOperationException("EquippedSlot cannot be set.");
    }

    public ArrayList<String> getRightEquipped(){
        return (ArrayList<String>) rightEquipped.clone();
    }

    private void setRightEquipped(ArrayList<String> s){
        throw new UnsupportedOperationException("EquippedSlot cannot be set.");
    }
}
