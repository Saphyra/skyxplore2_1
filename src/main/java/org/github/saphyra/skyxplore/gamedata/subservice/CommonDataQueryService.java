package org.github.saphyra.skyxplore.gamedata.subservice;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonDataQueryService extends AbstractGameDataService<GeneralDescription> {
    private final List<AbstractGameDataService<? extends GeneralDescription>> services;

    @Override
    @PostConstruct
    public void init() {
        services.forEach(this::loadItems);
    }

    private void loadItems(AbstractGameDataService<? extends GeneralDescription> service) {
        service.entrySet().forEach(entry ->{
            log.debug("Loaded item: {}", entry.toString());
            put(entry.getKey(), entry.getValue());
        });
    }

    public List<String> getItemsOfCategory(String categoryId) {
        return values().stream()
            .filter(generalDescription -> generalDescription.getCategory().equals(categoryId))
            .map(GeneralDescription::getId)
            .collect(Collectors.toList());
    }
}
