package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingQueryService;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FillWithAiService {
    private final ExistingGroupFiller existingGroupFiller;
    private final FillWithAiGroupsService fillWithAiGroupsService;
    private final GameGroupingQueryService gameGroupingQueryService;

    public void fillWithAi() {
        log.info("Filling GameGroupings with AI-s...");
        try {
            gameGroupingQueryService.getAutoFillableGameGroupings()
                .forEach(this::fillWithAi);
        } catch (Exception e) {
            log.error("Error occurred during autofill:", e);
        }
    }

    private void fillWithAi(GameGrouping gameGrouping) {
        log.info("Filling GameGrouping {} with ais...", gameGrouping.getGameGroupingId());
        int missingMembers = existingGroupFiller.fillEmptyPlacesInExistingGroups(gameGrouping);

        log.debug("All emptyPlaces are filled of GameGroups in GameGrouping {}. Remaining missingMembers: {}. Creating missing members...", gameGrouping.getGameGroupingId(), missingMembers);
        fillWithAiGroupsService.fillGameWithAiGroups(gameGrouping, missingMembers);
    }
}
