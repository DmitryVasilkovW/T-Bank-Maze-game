package backend.academy.maze.service.generator;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.model.type.SurfaceType;
import backend.academy.maze.service.generator.handler.chain.factory.impl.SurfaceTypeHandlerChainFactoryImpl;
import backend.academy.maze.service.generator.impl.DFSGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DFSGeneratorTest {
    private DFSGenerator generator;

    @BeforeEach
    void setUp() {
        List<Coordinate> directionsForGen = List.of(
                new Coordinate(0, 1),
                new Coordinate(1, 0),
                new Coordinate(0, -1),
                new Coordinate(-1, 0)
        );

        generator = new DFSGenerator(new SurfaceTypeHandlerChainFactoryImpl().create(), directionsForGen);
    }

    @Test
    @DisplayName("Checking width and height")
    void testMazeGenerationHasCorrectSize() {
        int height = 10;
        int width = 10;
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(height - 1, width - 1);
        Maze maze = generator.generate(height, width, start, end);

        assertEquals(height, maze.height());
        assertEquals(width, maze.width());
    }

    @Test
    @DisplayName("maze entry point check")
    void testStartPositionIsPassage() {
        int height = 10;
        int width = 10;
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(height - 1, width - 1);
        Maze maze = generator.generate(height, width, start, end);

        Cell startCell = maze.getCell(start.row(), start.col());
        assertEquals(SurfaceType.START, startCell.surface(), "The starting cage should be marked as START.");
        assertEquals(PassageType.PASSAGE, startCell.type(), "The starting cage should be a passageway.");
    }

    @Test
    @DisplayName("maze exit point check")
    void testEndPositionIsPassage() {
        int height = 10;
        int width = 10;
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(height - 1, width - 1);
        Maze maze = generator.generate(height, width, start, end);

        Cell endCell = maze.getCell(end.row(), end.col());
        assertEquals(SurfaceType.END, endCell.surface(), "The ending cage should be marked as END.");
        assertEquals(PassageType.PASSAGE, endCell.type(), "The ending cage should be a passageway.");
    }

    @Test
    @DisplayName("Checking the correctness of the maze parts")
    void testAllCellsAreEitherPassageOrWall() {
        int height = 10;
        int width = 10;
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(height - 1, width - 1);
        Maze maze = generator.generate(height, width, start, end);

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                PassageType cellType = maze.getCell(row, col).type();
                assertTrue(cellType == PassageType.PASSAGE || cellType == PassageType.WALL,
                        "All cells must be either passages or walls.");
            }
        }
    }

    @Test
    @DisplayName("check that all surfaces in the maze have been generated")
    void testSurfaceDistribution() {
        int height = 100;
        int width = 100;
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(height - 1, width - 1);
        Maze maze = generator.generate(height, width, start, end);

        int mudCount = 0;
        int sandCount = 0;
        int coinCount = 0;
        int normalCount = 0;

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.type() == PassageType.PASSAGE) {
                    switch (cell.surface()) {
                        case MUD:
                            mudCount++;
                            break;
                        case SAND:
                            sandCount++;
                            break;
                        case COIN:
                            coinCount++;
                            break;
                        case NORMAL:
                            normalCount++;
                            break;
                    }
                }
            }
        }

        assertTrue(mudCount > 0, "There must be cells with a MUD surface.");
        assertTrue(sandCount > 0, "There must be cells with a SAND surface.");
        assertTrue(coinCount > 0, "There should be cells with coins.");
        assertTrue(normalCount > 0, "There should be cells with a normal surface.");
    }

    @Test
    @DisplayName("Checking if the maze is passable")
    void testMazeHasCorrectPassageStructure() {
        int height = 15;
        int width = 15;
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(height - 1, width - 1);
        Maze maze = generator.generate(height, width, start, end);

        Set<Coordinate> visited = new HashSet<>();
        dfsValidate(maze, start, visited);

        assertTrue(visited.contains(end), "End point must be reachable from the start.");

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.type() == PassageType.PASSAGE) {
                    assertTrue(visited.contains(new Coordinate(row, col)),
                            "All passages must be reachable.");
                }
            }
        }
    }


    private void dfsValidate(Maze maze, Coordinate coord, Set<Coordinate> visited) {
        if (coord.row() < 0 || coord.row() >= maze.height() ||
                coord.col() < 0 || coord.col() >= maze.width()) {
            return;
        }

        Cell cell = maze.getCell(coord.row(), coord.col());
        if (cell.type() != PassageType.PASSAGE || visited.contains(coord)) {
            return;
        }

        visited.add(coord);

        dfsValidate(maze, new Coordinate(coord.row() + 1, coord.col()), visited);
        dfsValidate(maze, new Coordinate(coord.row() - 1, coord.col()), visited);
        dfsValidate(maze, new Coordinate(coord.row(), coord.col() + 1), visited);
        dfsValidate(maze, new Coordinate(coord.row(), coord.col() - 1), visited);
    }
}
