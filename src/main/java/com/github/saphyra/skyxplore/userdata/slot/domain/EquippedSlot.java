package com.github.saphyra.skyxplore.userdata.slot.domain;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EquippedSlot {
    @NonNull
    private final String slotId;

    @NonNull
    private final String shipId;

    @NonNull
    private Integer frontSlot;

    @Builder.Default
    @NonNull
    private List<String> frontEquipped = new ArrayList<>();

    @NonNull
    private Integer leftSlot;

    @Builder.Default
    @NonNull
    private List<String> leftEquipped = new ArrayList<>();

    @NonNull
    private Integer rightSlot;

    @Builder.Default
    @NonNull
    private List<String> rightEquipped = new ArrayList<>();

    @NonNull
    private Integer backSlot;

    @Builder.Default
    @NonNull
    private List<String> backEquipped = new ArrayList<>();

    public void addSlot(Integer extraSlot) {
        frontSlot += extraSlot;
        backSlot += extraSlot;
        leftSlot += extraSlot;
        rightSlot += extraSlot;
    }

    public void removeSlot(SkyXpCharacter character, Integer removedSlot) {
        frontSlot -= removedSlot;
        backSlot -= removedSlot;
        leftSlot -= removedSlot;
        rightSlot -= removedSlot;

        while (frontSlot < frontEquipped.size()) {
            character.addEquipment(frontEquipped.get(0));
            frontEquipped.remove(0);
        }
        while (backSlot < backEquipped.size()) {
            character.addEquipment(backEquipped.get(0));
            backEquipped.remove(0);
        }
        while (leftSlot < leftEquipped.size()) {
            character.addEquipment(leftEquipped.get(0));
            leftEquipped.remove(0);
        }
        while (rightSlot < rightEquipped.size()) {
            character.addEquipment(rightEquipped.get(0));
            rightEquipped.remove(0);
        }
    }

    public void addFront(String element) {
        if (frontSlot.equals(frontEquipped.size())) {
            throw new BadRequestException("Front slot is full.");
        }
        frontEquipped.add(element);
    }

    public void addFront(Collection<String> elements) {
        elements.forEach(this::addFront);
    }

    public void removeFront(String element) {
        if (!frontEquipped.remove(element)) {
            throw new BadRequestException("Front slot does not contain element " + element);
        }
    }

    public void addLeft(String element) {
        if (leftSlot.equals(leftEquipped.size())) {
            throw new BadRequestException("Left slot is full.");
        }
        leftEquipped.add(element);
    }

    public void addLeft(Collection<String> elements) {
        elements.forEach(this::addLeft);
    }

    public void removeLeft(String element) {
        if (!leftEquipped.remove(element)) {
            throw new BadRequestException("Left slot does not contain element " + element);
        }
    }

    public void addRight(String element) {
        if (rightSlot.equals(rightEquipped.size())) {
            throw new BadRequestException("Right slot is full.");
        }
        rightEquipped.add(element);
    }

    public void addRight(Collection<String> elements) {
        elements.forEach(this::addRight);
    }

    public void removeRight(String element) {
        if (!rightEquipped.remove(element)) {
            throw new BadRequestException("Right slot does not contain element " + element);
        }
    }

    public void addBack(String element) {
        if (backSlot.equals(backEquipped.size())) {
            throw new BadRequestException("Back slot is full.");
        }
        backEquipped.add(element);
    }

    public void addBack(Collection<String> elements) {
        elements.forEach(this::addBack);
    }

    public void removeBack(String element) {
        if (!backEquipped.remove(element)) {
            throw new BadRequestException("Back slot does not contain element " + element);
        }
    }

    public List<String> getFrontEquipped() {
        return new ArrayList<>(frontEquipped);
    }

    @SuppressWarnings("unused")
    private void setFrontEquipped(ArrayList<String> s) {
        throw new UnsupportedOperationException("EquippedSlot cannot be set.");
    }

    public List<String> getBackEquipped() {
        return new ArrayList<>(backEquipped);
    }

    @SuppressWarnings("unused")
    private void setBackEquipped(ArrayList<String> s) {
        throw new UnsupportedOperationException("EquippedSlot cannot be set.");
    }

    public List<String> getLeftEquipped() {
        return new ArrayList<>(leftEquipped);
    }

    @SuppressWarnings("unused")
    private void setLeftEquipped(ArrayList<String> s) {
        throw new UnsupportedOperationException("EquippedSlot cannot be set.");
    }

    public List<String> getRightEquipped() {
        return new ArrayList<>(rightEquipped);
    }

    @SuppressWarnings("unused")
    private void setRightEquipped(ArrayList<String> s) {
        throw new UnsupportedOperationException("EquippedSlot cannot be set.");
    }
}
