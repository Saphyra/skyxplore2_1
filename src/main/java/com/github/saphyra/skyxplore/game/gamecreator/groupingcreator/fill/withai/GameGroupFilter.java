package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class GameGroupFilter {
    List<GameGroup> getAutoFillableGameGroups(List<GameGroup> gameGroups) {
        return gameGroups.stream()
            .filter(gameGroup -> gameGroup.getCharacters().size() < gameGroup.getCharacters().getMaxSize())
            .filter(GameGroup::isAutoFill)
            .collect(Collectors.toList());
    }
}
