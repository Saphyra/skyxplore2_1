package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.gamedata.entity.Weapon;
import com.github.saphyra.skyxplore.data.gamedata.subservice.WeaponService;
import com.github.saphyra.skyxplore.game.game.domain.ship.WeaponSideDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeaponSideDetailsFactory {
    private final WeaponService weaponService;

    public List<WeaponSideDetails> create(List<String> items) {
        List<WeaponSideDetails> weaponSideDetails = items.stream()
            .map(this::createWeaponSideDetails)
            .collect(Collectors.toList());
        log.debug("Created WeaponSideDetails: {}", weaponSideDetails);
        return weaponSideDetails;
    }

    private WeaponSideDetails createWeaponSideDetails(String itemId) {
        Weapon weapon = Optional.ofNullable(weaponService.get(itemId))
            .orElseThrow(() -> new RuntimeException("Weapon not found with itemId " + itemId));
        return WeaponSideDetails.builder()
            .itemId(itemId)
            .attackSpeed(weapon.getAttackSpeed())
            .range(weapon.getRange())
            .criticalRate(weapon.getCriticalRate())
            .hullDamage(weapon.getHullDamage())
            .shieldDamage(weapon.getShieldDamage())
            .accuracy(weapon.getAccuracy())
            .build();
    }
}
