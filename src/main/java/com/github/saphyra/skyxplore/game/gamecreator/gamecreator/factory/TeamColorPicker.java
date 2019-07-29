package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.TeamColor;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class TeamColorPicker {
    private final Random random;

    TeamColor getRandomColor(List<TeamColor> usedColors) {
        List<TeamColor> availableColors = Arrays.stream(TeamColor.values())
            .filter(teamColor -> !usedColors.contains(teamColor))
            .collect(Collectors.toList());

        return availableColors.get(random.randInt(0, availableColors.size() - 1));
    }
}
