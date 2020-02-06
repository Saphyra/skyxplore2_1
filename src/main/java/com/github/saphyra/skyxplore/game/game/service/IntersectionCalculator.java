package com.github.saphyra.skyxplore.game.game.service;

import com.github.saphyra.skyxplore.game.game.domain.ship.Coordinates;
import com.github.saphyra.skyxplore.game.game.domain.ship.Vector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class IntersectionCalculator {
    public Optional<Coordinates> calculateCrossPoint(Vector v1, Vector v2) {
        Coordinates a = v1.getStartPoint();
        Coordinates b = v1.getEndPoint();
        Coordinates c = v2.getStartPoint();
        Coordinates d = v2.getEndPoint();

        double a1 = b.getYCoord() - a.getYCoord();
        double b1 = a.getXCoord() - b.getXCoord();
        double c1 = a1 * a.getXCoord() + b1 * a.getYCoord();

        double a2 = d.getYCoord() - c.getYCoord();
        double b2 = c.getXCoord() - d.getXCoord();
        double c2 = a2 * c.getXCoord() + b2 * c.getYCoord();

        double determinant = a1 * b2 - a2 * b1;
        if (determinant == 0) {
            return Optional.empty();
        } else {
            double x = (b2 * c1 - b1 * c2) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;
            log.info("x: {}, y: {}", x, y);
            Coordinates intersection = Coordinates.builder()
                .xCoord(x)
                .yCoord(y)
                .build();
            return Optional.of(intersection);
        }
    }
}
