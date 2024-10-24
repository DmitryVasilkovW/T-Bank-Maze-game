package backend.academy.maze.service.launcher.direction.factory.impl;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.service.launcher.direction.factory.DirectionFactory;
import java.util.List;

public class DirectionFactoryImpl implements DirectionFactory {

    @Override
    public List<Coordinate> createDirectionAsList(boolean isDiagonalMovementPossible) {
        if (isDiagonalMovementPossible) {
            return List.of(
                    new Coordinate(0, 1),
                    new Coordinate(1, 0),
                    new Coordinate(0, -1),
                    new Coordinate(-1, 0),
                    new Coordinate(1, 1),
                    new Coordinate(1, -1),
                    new Coordinate(-1, 1),
                    new Coordinate(-1, -1)
            );
        }
        return List.of(
                new Coordinate(0, 1),
                new Coordinate(1, 0),
                new Coordinate(0, -1),
                new Coordinate(-1, 0));
    }

    @Override
    public int[][] createDirectionAsMatrix(boolean isDiagonalMovementPossible) {
        if (isDiagonalMovementPossible) {
            return new int[][] {
                    {0, 1},
                    {0, -1},
                    {1, 0},
                    {-1, 0},
                    {1, 1},
                    {1, -1},
                    {-1, 1},
                    {-1, -1}
            };
        }
        return new int[][] {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0},
        };
    }
}
