package backend.academy.maze.service.solver;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.model.TestCaseForSurface;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.model.type.SurfaceType;
import backend.academy.maze.service.solver.handler.chain.factory.impl.CostHandlerChainFactoryImpl;
import backend.academy.maze.service.solver.impl.AStarSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AStarSolverTest {
    CostHandlerChainFactoryImpl factory = new CostHandlerChainFactoryImpl();
    int[][] directions = {
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0},
    };
    @Spy
    private Maze maze = new Maze(5, 5);
    @Spy
    private AStarSolver solver = new AStarSolver(directions, factory.create(), maze);

    @BeforeEach
    void setUp() {
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

    @ParameterizedTest
    @MethodSource("pathProviderWithBadSurfaces")
    @DisplayName("Test solving maze with different surfaces")
    void testSolveWithDifferentBadSurfaces(TestCaseForSurface testCase) {
        maze.getCell(1, 0).type(PassageType.PASSAGE);
        maze.getCell(1, 0).type(PassageType.PASSAGE);
        maze.getCell(2, 0).type(PassageType.PASSAGE);
        maze.getCell(2, 1).type(PassageType.PASSAGE);
        maze.getCell(testCase.dirtCoordinate().row(), testCase.dirtCoordinate().col()).surface(testCase.surface());

        List<Coordinate> path = solver.solve(testCase.start(), testCase.end());

        assertFalse(path.contains(testCase.dirtCoordinate()), "The path must avoid the position with " + testCase.surface());
        assertEquals(testCase.end(), path.getLast(), "The path must end at the final position");
    }

    static Stream<TestCaseForSurface> pathProviderWithBadSurfaces() {
        return Stream.of(
                new TestCaseForSurface(
                        new Coordinate(0, 0),
                        new Coordinate(4, 2),
                        new Coordinate(1, 2),
                        SurfaceType.SAND
                ),
                new TestCaseForSurface(
                        new Coordinate(0, 0),
                        new Coordinate(4, 2),
                        new Coordinate(1, 2),
                        SurfaceType.MUD
                )
        );
    }

    @ParameterizedTest
    @MethodSource("pathProviderWithGoodSurfaces")
    @DisplayName("Test solving maze with different surfaces")
    void testSolveWithDifferentGoodSurfaces(TestCaseForSurface testCase) {
        maze.getCell(1, 0).type(PassageType.PASSAGE);
        maze.getCell(1, 0).type(PassageType.PASSAGE);
        maze.getCell(2, 0).type(PassageType.PASSAGE);
        maze.getCell(2, 1).type(PassageType.PASSAGE);
        maze.getCell(testCase.dirtCoordinate().row(), testCase.dirtCoordinate().col()).surface(testCase.surface());

        List<Coordinate> path = solver.solve(testCase.start(), testCase.end());

        assertTrue(path.contains(testCase.dirtCoordinate()), "has to go over a good surface " + testCase.surface());
        assertEquals(testCase.end(), path.getLast(), "The path must end at the final position");
    }

    static Stream<TestCaseForSurface> pathProviderWithGoodSurfaces() {
        return Stream.of(
                new TestCaseForSurface(
                        new Coordinate(0, 0),
                        new Coordinate(4, 2),
                        new Coordinate(1, 2),
                        SurfaceType.COIN
                )
        );
    }

}

