package com.github.saphyra.skyxplore.data.gamedata.subservice;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.GeneralDescription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonGameDataQueryService extends AbstractDataService<GeneralDescription> {
    private final List<AbstractDataService<? extends GeneralDescription>> services;

    @Override
    @PostConstruct
    public void init() {
        services.forEach(this::loadItems);
    }

    private void loadItems(AbstractDataService<? extends GeneralDescription> service) {
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
