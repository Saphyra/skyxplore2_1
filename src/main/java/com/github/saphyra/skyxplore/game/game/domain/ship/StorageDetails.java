package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class StorageDetails {
    private final int capacity;
    private final Map<String, Integer> items = new ConcurrentHashMap<>();
}
