package backend.academy.maze.service.solver;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.TestCaseForSurface;
import backend.academy.maze.service.solver.impl.AStarSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AStarSolverTest {

    private AStarSolver solver;
    private Maze maze;

    @BeforeEach
    void setUp() {
        int[][] directions = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0},
        };
        solver = new AStarSolver(directions);
        maze = new Maze(5, 5);

        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                maze.getCell(row, col).setType(Cell.Type.WALL);
            }
        }

        maze.getCell(0, 0).setType(Cell.Type.PASSAGE);
        maze.getCell(0, 1).setType(Cell.Type.PASSAGE);
        maze.getCell(0, 2).setType(Cell.Type.PASSAGE);
        maze.getCell(1, 2).setType(Cell.Type.PASSAGE);
        maze.getCell(2, 2).setType(Cell.Type.PASSAGE);
        maze.getCell(3, 2).setType(Cell.Type.PASSAGE);
        maze.getCell(4, 2).setType(Cell.Type.PASSAGE);
    }

    @Test
    @DisplayName("Test solving maze with a valid path")
    void testSolveValidPath() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 2);

        List<Coordinate> path = solver.solve(maze, start, end);

        assertNotNull(path, "The path should not be null");
        assertFalse(path.isEmpty(), "The path should not be empty");
        assertEquals(start, path.getFirst(), "The path should start at the start position");
        assertEquals(end, path.getLast(), "The path should end at the end position");
    }

    @Test
    @DisplayName("Test solving maze with no possible path")
    void testSolveNoPath() {
        maze.getCell(3, 2).setType(Cell.Type.WALL);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 2);

        List<Coordinate> path = solver.solve(maze, start, end);

        assertTrue(path.isEmpty(), "If there is no valid path, the result should be an empty list");
    }

    @Test
    @DisplayName("Test solving with start equal to end")
    void testSolveStartEqualsEnd() {
        Coordinate start = new Coordinate(0, 0);

        List<Coordinate> path = solver.solve(maze, start, start);

        assertEquals(1, path.size(), "The path should contain only the start/end position if they are the same");
        assertEquals(start, path.getFirst(), "The path should consist of the start position");
    }

    @ParameterizedTest
    @MethodSource("pathProviderWithBadSurfaces")
    @DisplayName("Test solving maze with different surfaces")
    void testSolveWithDifferentBadSurfaces(TestCaseForSurface testCase) {
        maze.getCell(1, 0).setType(Cell.Type.PASSAGE);
        maze.getCell(1, 0).setType(Cell.Type.PASSAGE);
        maze.getCell(2, 0).setType(Cell.Type.PASSAGE);
        maze.getCell(2, 1).setType(Cell.Type.PASSAGE);
        maze.getCell(testCase.dirtCoordinate().row(), testCase.dirtCoordinate().col()).setSurface(testCase.surface());

        List<Coordinate> path = solver.solve(maze, testCase.start(), testCase.end());

        assertFalse(path.contains(testCase.dirtCoordinate()), "The path must avoid the position with " + testCase.surface());
        assertEquals(testCase.end(), path.getLast(), "The path must end at the final position");
    }

    static Stream<TestCaseForSurface> pathProviderWithBadSurfaces() {
        return Stream.of(
                new TestCaseForSurface(
                        new Coordinate(0, 0),
                        new Coordinate(4, 2),
                        new Coordinate(1, 2),
                        Cell.Surface.SAND
                ),
                new TestCaseForSurface(
                        new Coordinate(0, 0),
                        new Coordinate(4, 2),
                        new Coordinate(1, 2),
                        Cell.Surface.MUD
                )
        );
    }

    @ParameterizedTest
    @MethodSource("pathProviderWithGoodSurfaces")
    @DisplayName("Test solving maze with different surfaces")
    void testSolveWithDifferentGoodSurfaces(TestCaseForSurface testCase) {
        maze.getCell(1, 0).setType(Cell.Type.PASSAGE);
        maze.getCell(1, 0).setType(Cell.Type.PASSAGE);
        maze.getCell(2, 0).setType(Cell.Type.PASSAGE);
        maze.getCell(2, 1).setType(Cell.Type.PASSAGE);
        maze.getCell(testCase.dirtCoordinate().row(), testCase.dirtCoordinate().col()).setSurface(testCase.surface());

        List<Coordinate> path = solver.solve(maze, testCase.start(), testCase.end());

        assertTrue(path.contains(testCase.dirtCoordinate()), "has to go over a good surface " + testCase.surface());
        assertEquals(testCase.end(), path.getLast(), "The path must end at the final position");
    }

    static Stream<TestCaseForSurface> pathProviderWithGoodSurfaces() {
        return Stream.of(
                new TestCaseForSurface(
                        new Coordinate(0, 0),
                        new Coordinate(4, 2),
                        new Coordinate(1, 2),
                        Cell.Surface.COIN
                )
        );
    }

    @Test
    @DisplayName("Test move cost calculation using reflection")
    void testMoveCostCalculationUsingReflection() throws Exception {
        Cell cell = new Cell(0, 0, null);

        Method getMoveCostMethod = AStarSolver.class.getDeclaredMethod("getMoveCost", Cell.class);
        getMoveCostMethod.setAccessible(true);

        cell.setSurface(Cell.Surface.NORMAL);
        assertEquals(1.0, getMoveCostMethod.invoke(solver, cell));

        cell.setSurface(Cell.Surface.MUD);
        assertEquals(5.0, getMoveCostMethod.invoke(solver, cell));

        cell.setSurface(Cell.Surface.SAND);
        assertEquals(3.0, getMoveCostMethod.invoke(solver, cell));

        cell.setSurface(Cell.Surface.COIN);
        assertEquals(-2.0, getMoveCostMethod.invoke(solver, cell));
    }

    @Test
    @DisplayName("Test heuristic calculation using reflection")
    void testHeuristicCalculationUsingReflection() throws Exception {
        Method heuristicMethod = AStarSolver.class.getDeclaredMethod("heuristic", Coordinate.class, Coordinate.class);
        heuristicMethod.setAccessible(true);

        Coordinate a = new Coordinate(0, 0);
        Coordinate b = new Coordinate(4, 2);

        double heuristic = (double) heuristicMethod.invoke(solver, a, b);

        assertEquals(6.0, heuristic, "The heuristic should be the Manhattan distance between the two points");
    }

    @Test
    @DisplayName("Test neighbors retrieval using reflection")
    void testGetNeighborsUsingReflection() throws Exception {
        Method getNeighborsMethod = AStarSolver.class.getDeclaredMethod("getNeighbors", Coordinate.class, Maze.class);
        getNeighborsMethod.setAccessible(true);

        Coordinate coord = new Coordinate(1, 2);

        List<Coordinate> neighbors = (List<Coordinate>) getNeighborsMethod.invoke(solver, coord, maze);

        assertEquals(2, neighbors.size(), "The coordinate (1,2) should have three valid neighbors");
        assertTrue(neighbors.contains(new Coordinate(0, 2)), "The top neighbor should be (0,2)");
        assertTrue(neighbors.contains(new Coordinate(2, 2)), "The bottom neighbor should be (2,2)");
    }

}

