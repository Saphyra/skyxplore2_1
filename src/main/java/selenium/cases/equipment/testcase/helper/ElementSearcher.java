package selenium.cases.equipment.testcase.helper;

import static java.lang.Math.toIntExact;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import lombok.RequiredArgsConstructor;
import selenium.cases.equipment.testcase.domain.ContainerId;
import selenium.cases.equipment.testcase.domain.EquippedEquipment;
import selenium.cases.equipment.testcase.domain.UnequippedEquipment;

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
        return toIntExact(findEquippedEquipmentsOfContainer(ContainerId.FRONT_WEAPON).stream()
            .filter(ElementSearcher.IS_EMPTY_SLOT)
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
}
