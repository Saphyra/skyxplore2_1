package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.GameCharacter;
import com.github.saphyra.skyxplore.game.game.domain.Team;
import com.github.saphyra.skyxplore.game.game.domain.TeamColor;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TeamFactory {
    private final GameCharacterFactory gameCharacterFactory;
    private final IdGenerator idGenerator;
    private final TeamColorPicker teamColorPicker;

    public List<Team> create(GameGrouping gameGrouping) {
        List<TeamColor> usedColors = new ArrayList<>();
        return gameGrouping.getGameGroups().stream()
            .map(gameGroup -> create(gameGroup, usedColors))
            .collect(Collectors.toList());
    }

    private Team create(GameGroup gameGroup, List<TeamColor> usedColors) {
        TeamColor teamColor = teamColorPicker.getRandomColor(usedColors);
        usedColors.add(teamColor);

        List<GameCharacter> gameCharacters = gameCharacterFactory.create(gameGroup.getCharacters());
        Team team = Team.builder()
            .teamId(idGenerator.randomUUID())
            .teamColor(teamColor)
            .build()
            .addCharacters(gameCharacters);
        log.debug("Team created: {}", team);
        return team;
    }
}
