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
        double a1 = b.getYCoord() - a.getYCoord();
        double b1 = a.getXCoord() - b.getXCoord();
        double c1 = a1 * a.getXCoord() + b1 * a.getYCoord();

        double a2 = d.getYCoord() - c.getYCoord();
        double b2 = c.getXCoord() - d.getXCoord();
        double c2 = a2 * c.getXCoord() + b2 * c.getYCoord();

        double determinant = a1 * b2 - a2 * b1;
        if (determinant == 0) {
            log.info("Lines are parallel.");
        } else {
            double x = (b2 * c1 - b1 * c2) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;
            log.info("x: {}, y: {}", x, y);
            Coordinates intersection = Coordinates.builder()
                .xCoord((int) Math.round(x))
                .yCoord((int) Math.round(y))
                .build();
            log.info("Intersection: {}", intersection);
        }
        //THEN
    }

    private int getRandomValue() {
        return random.randInt(-5, 5);
    }
}
