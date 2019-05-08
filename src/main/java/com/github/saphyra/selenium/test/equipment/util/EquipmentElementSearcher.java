package com.github.saphyra.selenium.test.equipment.util;

import com.github.saphyra.selenium.logic.domain.EquippedEquipment;
import com.github.saphyra.selenium.logic.domain.UnequippedEquipment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.github.saphyra.selenium.logic.domain.ContainerId;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;
import static com.github.saphyra.selenium.logic.util.WaitUtil.getWithWait;

@RequiredArgsConstructor
@Slf4j
public class EquipmentElementSearcher {
    private static final Predicate<EquippedEquipment> IS_EMPTY_SLOT = EquippedEquipment::isEmpty;
    private static final String PREFIX_SELECTOR_SLOT = "#%s .slot";
    private static final String SELECTOR_UNEQUIPPED_ITEMS = "#equipment-list div.equipment-list-element";
    private static final String PREFIX_SELECTOR_EMPTY_SLOT = "#%s div.empty-slot";

    private final WebDriver driver;

    private List<EquippedEquipment> findEquippedEquipmentsOfContainer(ContainerId containerId) {
        return driver.findElements(By.cssSelector(String.format(PREFIX_SELECTOR_SLOT, containerId.getId()))).stream()
            .map(EquippedEquipment::new)
            .collect(Collectors.toList());
    }

    public int countEmptySlotsInContainer(ContainerId containerId) {
        return toIntExact(findEquippedEquipmentsOfContainer(containerId).stream()
            .filter(IS_EMPTY_SLOT)
            .count());
    }

    public int countNonEmptySlotsInContainer(ContainerId containerId) {
        return toIntExact(findEquippedEquipmentsOfContainer(containerId).stream()
            .filter(equippedEquipment -> !IS_EMPTY_SLOT.test(equippedEquipment))
            .count());
    }

    public EquippedEquipment findAnyEquippedFromContainer(ContainerId containerId) {
        log.info("Finding an equipped equipment in container {}", containerId);
        Optional<EquippedEquipment> foundEquipment;
        int counter = 0;
        do {
            log.info("Finding attempt {}", counter);
            foundEquipment = findEquippedEquipmentsOfContainer(containerId).stream()
                .filter(equippedEquipment -> !IS_EMPTY_SLOT.test(equippedEquipment))
                .findAny();
            counter++;
        } while (!foundEquipment.isPresent() && counter < 100);

        return foundEquipment
            .orElseThrow(() -> new RuntimeException("No equipped element found in container " + containerId.name()));
    }

    public List<UnequippedEquipment> getAllUnequippedEquipments() {
        return driver.findElements(By.cssSelector(SELECTOR_UNEQUIPPED_ITEMS)).stream()
            .map(UnequippedEquipment::new)
            .collect(Collectors.toList());
    }

    public UnequippedEquipment getUnequippedEquipmentById(String itemId) {
        Supplier<Optional<UnequippedEquipment>> supplier = () -> getAllUnequippedEquipments().stream()
            .filter(unequippedEquipment -> itemId.equalsIgnoreCase(unequippedEquipment.getId()))
            .findAny();
        return getWithWait(supplier, "Querying unequippedEquipment with id " + itemId)
            .orElseThrow(() -> new RuntimeException("Unequipped equipment not found with itemId " + itemId));
    }


    private List<WebElement> getEmptySlotsOfContainer(ContainerId containerId) {
        return driver.findElements(By.cssSelector(String.format(PREFIX_SELECTOR_EMPTY_SLOT, containerId.getId())));
    }

    public WebElement getEmptySlotFromContainer(ContainerId containerId) {
        return getEmptySlotsOfContainer(containerId).stream()
            .findAny()
            .orElseThrow(() -> new RuntimeException("No empty slot found in container " + containerId.name()));
    }
}
