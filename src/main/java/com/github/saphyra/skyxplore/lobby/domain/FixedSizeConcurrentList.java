package com.github.saphyra.skyxplore.lobby.domain;

import java.util.Vector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@RequiredArgsConstructor
public class FixedSizeConcurrentList<T> extends Vector<T> {

    @Getter
    private final int maxSize;

    @Override
    public boolean add(T element) {
        if (super.size() == maxSize) {
            throw new ArrayIndexOutOfBoundsException("List has reached max size: " + maxSize);
        }
        return super.add(element);
    }
}
