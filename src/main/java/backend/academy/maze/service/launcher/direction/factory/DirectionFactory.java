package backend.academy.maze.service.launcher.direction.factory;

import backend.academy.maze.model.Coordinate;
import java.util.List;

public interface DirectionFactory {

    List<Coordinate> createDirectionAsList(boolean isDiagonalMovementPossible);

    int[][] createDirectionAsMatrix(boolean isDiagonalMovementPossible);
}
