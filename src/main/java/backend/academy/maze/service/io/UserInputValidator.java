package backend.academy.maze.service.io;

import backend.academy.maze.model.Coordinate;

public interface UserInputValidator {

    boolean isLineValid(String line);

    boolean isPointValid(int row, int column);

    boolean isMazeValid(Coordinate start, Coordinate end, int height, int width);
}
