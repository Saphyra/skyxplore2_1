package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.TeamColor;
import com.github.saphyra.util.Random;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Builder
class TeamColorPicker {
    private final Random random;
    private final List<TeamColor> teamColors;

    TeamColor getRandomColor(List<TeamColor> usedColors) {
        List<TeamColor> availableColors = teamColors.stream()
            .filter(teamColor -> !usedColors.contains(teamColor))
            .collect(Collectors.toList());

        return availableColors.get(random.randInt(0, availableColors.size() - 1));
    }
}
