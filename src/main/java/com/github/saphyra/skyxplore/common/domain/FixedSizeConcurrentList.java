package com.github.saphyra.skyxplore.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Vector;

@ToString(callSuper = true)
@RequiredArgsConstructor
public class FixedSizeConcurrentList<T> extends Vector<T> {

    @Getter
    private final int maxSize;

    @Override
    public synchronized boolean add(T element) {
        if (super.size() == maxSize) {
            throw new ArrayIndexOutOfBoundsException("List has reached max size: " + maxSize);
        }
        return super.add(element);
    }
}
