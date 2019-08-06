package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.ship.DefenseSideDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefenseSideDetailsFactory {
    private final HullDetailsFactory hullDetailsFactory;
    private final ShieldDetailsFactory shieldDetailsFactory;

    public DefenseSideDetails create(List<String> items) {
        DefenseSideDetails defenseSideDetails = DefenseSideDetails.builder()
            .hulls(hullDetailsFactory.create(items))
            .shields(shieldDetailsFactory.create(items))
            .build();
        log.debug("Created DefenseSideDetails: {}", defenseSideDetails);
        return defenseSideDetails;
    }
}
