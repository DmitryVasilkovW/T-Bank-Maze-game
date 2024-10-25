package backend.academy.maze.service.io.impl;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.service.io.UserInputValidator;

public class UserInputValidatorImpl implements UserInputValidator {
    private static final int CRITICAL_SIZE = 2;
    private static final int CRITICAL_POSITION = 0;

    @Override
    public boolean isLineValid(String line) {
        try {
            Integer.parseInt(line.split(" ")[0]);
            Integer.parseInt(line.split(" ")[1]);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isPointValid(int row, int column) {
        return row > CRITICAL_POSITION
                && column > CRITICAL_POSITION;
    }

    @Override
    public boolean isMazeValid(Coordinate start, Coordinate end, int height, int width) {
        return (height > CRITICAL_SIZE
                && width > CRITICAL_SIZE
                && isCoordinateValid(start, height, width)
                && isCoordinateValid(end, height, width));
    }

    private boolean isCoordinateValid(Coordinate coordinate, int height, int width) {
        return isPointOfCoordinateValid(coordinate.row(), width)
                && isPointOfCoordinateValid(coordinate.col(), height);
    }

    private boolean isPointOfCoordinateValid(int point, int size) {
        return point > 0 && point < size;
    }
}
