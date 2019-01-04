package selenium.cases.equipment.testcase.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.domain.ContainerId;
import selenium.domain.EquippedEquipment;
import selenium.domain.UnequippedEquipment;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

@RequiredArgsConstructor
public class ElementSearcher {
    private static final Predicate<EquippedEquipment> IS_EMPTY_SLOT = equippedEquipment -> equippedEquipment.getElement().getAttribute("class").contains("emptyslot");

    private final WebDriver driver;

    public List<EquippedEquipment> findEquippedEquipmentsOfContainer(ContainerId containerId) {
        return driver.findElements(By.cssSelector(String.format("#%s .slot", containerId.getId()))).stream()
            .map(EquippedEquipment::new)
            .collect(Collectors.toList());
    }

    public int countEmptySlotsInContainer(ContainerId containerId) {
        return toIntExact(findEquippedEquipmentsOfContainer(containerId).stream()
            .filter(IS_EMPTY_SLOT::test)
            .count());
    }

    public int countNonEmptySlotsInContainer(ContainerId containerId) {
        return toIntExact(findEquippedEquipmentsOfContainer(containerId).stream()
            .filter(equippedEquipment -> !IS_EMPTY_SLOT.test(equippedEquipment))
            .count());
    }

    public EquippedEquipment findAnyEquippedFromContainer(ContainerId containerId) {
        return findEquippedEquipmentsOfContainer(containerId).stream()
            .filter(equippedEquipment -> !IS_EMPTY_SLOT.test(equippedEquipment))
            .findAny()
            .orElseThrow(() -> new RuntimeException("No equipped element found in container " + containerId.name()));
    }

    public List<UnequippedEquipment> getAllUnequippedEquipments() {
        return driver.findElements(By.cssSelector("#equipmentlist div.equipmentlistelement")).stream()
            .map(UnequippedEquipment::new)
            .collect(Collectors.toList());
    }

    public UnequippedEquipment getUnequippedEquipmentById(String itemId) {
        return getAllUnequippedEquipments().stream()
            .filter(unequippedEquipment -> itemId.equalsIgnoreCase(unequippedEquipment.getId()))
            .findAny()
            .orElseThrow(() -> new RuntimeException("Unequipped equipment not found with id " + itemId));
    }

    public List<WebElement> getEmptySlotsOfContainer(ContainerId containerId) {
        return driver.findElements(By.cssSelector(String.format("#%s div.emptyslot", containerId.getId())));
    }

    public WebElement getEmptySlotFromContainer(ContainerId containerId) {
        return getEmptySlotsOfContainer(containerId).stream()
            .findAny()
            .orElseThrow(() -> new RuntimeException("No empty slot found in container " + containerId.name()));
    }
}
