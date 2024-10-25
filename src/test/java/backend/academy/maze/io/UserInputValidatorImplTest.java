package backend.academy.maze.io;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.service.io.impl.UserInputValidatorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserInputValidatorImplTest {
    private final UserInputValidatorImpl validator = new UserInputValidatorImpl();

    @Test
    @DisplayName("Validate line input: valid inputs")
    void testIsLineValidValidInput() {
        assertTrue(validator.isLineValid("1 2"));
        assertTrue(validator.isLineValid("100 200"));
        assertTrue(validator.isLineValid("3 4"));
    }

    @Test
    @DisplayName("Validate line input: invalid inputs")
    void testIsLineValidInvalidInput() {
        assertFalse(validator.isLineValid("1"));
        assertFalse(validator.isLineValid("a b"));
        assertFalse(validator.isLineValid("1 b"));
    }

    @Test
    @DisplayName("Validate points: valid points")
    void testIsPointValidValidPoints() {
        assertTrue(validator.isPointValid(1, 1));
        assertTrue(validator.isPointValid(2, 2));
    }

    @Test
    @DisplayName("Validate points: invalid points")
    void testIsPointValidInvalidPoints() {
        assertFalse(validator.isPointValid(0, 0));
        assertFalse(validator.isPointValid(-1, -1));
    }

    @Test
    @DisplayName("Validate maze: valid maze")
    void testIsMazeValidValidMaze() {
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(2, 2);
        assertTrue(validator.isMazeValid(start, end, 3, 3));
    }

    @Test
    @DisplayName("Validate maze: invalid maze")
    void testIsMazeValidInvalidMaze() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);
        assertFalse(validator.isMazeValid(start, end, 3, 3));

        start = new Coordinate(1, 1);
        end = new Coordinate(2, 2);
        assertFalse(validator.isMazeValid(start, end, 2, 3));
        assertFalse(validator.isMazeValid(start, end, 3, 2));
    }
}
