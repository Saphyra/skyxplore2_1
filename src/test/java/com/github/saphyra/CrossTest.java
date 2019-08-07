package com.github.saphyra;

import org.junit.Test;

import com.github.saphyra.skyxplore.game.game.domain.ship.Coordinates;
import com.github.saphyra.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrossTest {
    private Random random = new Random();

    @Test
    public void calculateCrossPoint() {
        //GIVEN
        Coordinates a = Coordinates.builder().xCoord(getRandomValue()).yCoord(getRandomValue()).build();
        Coordinates b = Coordinates.builder().xCoord(getRandomValue()).yCoord(getRandomValue()).build();
        Coordinates c = Coordinates.builder().xCoord(getRandomValue()).yCoord(getRandomValue()).build();
        Coordinates d = Coordinates.builder().xCoord(getRandomValue()).yCoord(getRandomValue()).build();

        log.info("Point A: {}", a);
        log.info("Point B: {}", b);
        log.info("Point C: {}", c);
        log.info("Point D: {}", d);
        //WHEN
        double ma = ((double) b.getYCoord() - a.getYCoord()) / (b.getXCoord() - a.getXCoord());
        double mb = ((double) d.getYCoord() - c.getYCoord()) / (d.getXCoord() - c.getXCoord());

        log.info("ma: {}", ma);
        log.info("mb: {}", mb);

        double ba = a.getYCoord() - ma * a.getXCoord();
        double bb = c.getYCoord() - mb * c.getXCoord();

        log.info("ba: {}", ba);
        log.info("bb: {}", bb);
        //THEN
    }

    private int getRandomValue() {
        return random.randInt(-5, 5);
    }
}
