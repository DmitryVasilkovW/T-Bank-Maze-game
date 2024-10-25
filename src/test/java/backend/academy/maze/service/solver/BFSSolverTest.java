package backend.academy.maze.service.solver;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.service.solver.impl.BFSSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BFSSolverTest {
    private Solver solver;
    private Maze maze;

    @BeforeEach
    void setUp() {
        int[][] directions = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0},
        };
        maze = new Maze(5, 5);
        solver = new BFSSolver(directions, maze);

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                maze.getCell(row, col).type(PassageType.WALL);
            }
        }

        maze.getCell(0, 0).type(PassageType.PASSAGE);
        maze.getCell(0, 1).type(PassageType.PASSAGE);
        maze.getCell(0, 2).type(PassageType.PASSAGE);
        maze.getCell(1, 2).type(PassageType.PASSAGE);
        maze.getCell(2, 2).type(PassageType.PASSAGE);
        maze.getCell(3, 2).type(PassageType.PASSAGE);
        maze.getCell(4, 2).type(PassageType.PASSAGE);
    }

    @Test
    @DisplayName("Test solving maze with a valid path")
    void testSolveValidPath() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 2);

        List<Coordinate> path = solver.solve(start, end);

        assertNotNull(path, "The path should not be null");
        assertFalse(path.isEmpty(), "The path should not be empty");
        assertEquals(start, path.getFirst(), "The path should start at the start position");
        assertEquals(end, path.getLast(), "The path should end at the end position");
    }

    @Test
    @DisplayName("Test solving maze with no possible path")
    void testSolveNoPath() {
        maze.getCell(3, 2).type(PassageType.WALL);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 2);

        List<Coordinate> path = solver.solve(start, end);

        assertTrue(path.isEmpty(), "If there is no valid path, the result should be an empty list");
    }

    @Test
    @DisplayName("Test solving with start equal to end")
    void testSolveStartEqualsEnd() {
        Coordinate start = new Coordinate(0, 0);

        List<Coordinate> path = solver.solve(start, start);

        assertEquals(1, path.size(), "The path should contain only the start/end position if they are the same");
        assertEquals(start, path.getFirst(), "The path should consist of the start position");
    }

    @Test
    @DisplayName("Test solving maze with dead-end paths")
    void testSolveWithDeadEnds() {
        maze.getCell(2, 3).type(PassageType.PASSAGE);
        maze.getCell(3, 3).type(PassageType.PASSAGE);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 2);

        List<Coordinate> path = solver.solve(start, end);

        assertNotNull(path, "The path should not be null");
        assertFalse(path.isEmpty(), "The path should not be empty");
        assertEquals(start, path.getFirst(), "The path should start at the start position");
        assertEquals(end, path.getLast(), "The path should end at the end position");
    }
}

