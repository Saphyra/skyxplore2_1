package com.github.saphyra.skyxplore.common.domain;

import java.util.List;
import java.util.Vector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@RequiredArgsConstructor
public class FixedSizeConcurrentList<T> extends Vector<T> {

    @Getter
    private final int maxSize;

    public FixedSizeConcurrentList(int maxSize, List<T> elements){
        super(elements);
        this.maxSize = maxSize;
    }

    @Override
    public synchronized boolean add(T element) {
        if (super.size() == maxSize) {
            throw new ArrayIndexOutOfBoundsException("List has reached max size: " + maxSize);
        }
        return super.add(element);
    }
}
