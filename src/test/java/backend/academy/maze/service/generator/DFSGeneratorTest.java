package backend.academy.maze.service.generator;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.service.generator.impl.DFSGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DFSGeneratorTest {

    private final DFSGenerator generator = new DFSGenerator();

    @Test
    @DisplayName("Checking width and height")
    void testMazeGenerationHasCorrectSize() {
        int height = 10;
        int width = 10;
        Maze maze = generator.generate(height, width);

        assertEquals(height, maze.getHeight());
        assertEquals(width, maze.getWidth());
    }

    @Test
    @DisplayName("maze entry point check")
    void testStartPositionIsPassage() {
        Maze maze = generator.generate(10, 10);

        Cell startCell = maze.getCell(0, 0);
        assertEquals(Cell.Type.PASSAGE, startCell.getType(), "The starting cage should be a passageway.");
    }

    @Test
    @DisplayName("Checking the correctness of the maze parts")
    void testAllCellsAreEitherPassageOrWall() {
        Maze maze = generator.generate(10, 10);

        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell.Type cellType = maze.getCell(row, col).getType();
                assertTrue(cellType == Cell.Type.PASSAGE || cellType == Cell.Type.WALL,
                        "All cages must be either aisles or walls.");
            }
        }
    }

    @Test
    @DisplayName("check that all surfaces in the maze have been generated")
    void testSurfaceDistribution() {
        Maze maze = generator.generate(100, 100);
        int mudCount = 0;
        int sandCount = 0;
        int coinCount = 0;
        int normalCount = 0;

        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.getType() == Cell.Type.PASSAGE) {
                    switch (cell.getSurface()) {
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
        assertTrue(coinCount > 0, "There should be cages of coins.");
        assertTrue(normalCount > 0, "There should be cages with a regular surface.");
    }

    @Test
    @DisplayName("Checking if the maze is passable")
    void testMazeHasCorrectPassageStructure() {
        int height = 10;
        int width = 10;
        Maze maze = generator.generate(height, width);

        Set<Coordinate> visited = new HashSet<>();
        dfsValidate(maze, new Coordinate(0, 0), visited);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.getType() == Cell.Type.PASSAGE) {
                    assertTrue(visited.contains(new Coordinate(row, col)),
                            "Maze must have a passage structure.");
                }
            }
        }
    }

    private void dfsValidate(Maze maze, Coordinate coord, Set<Coordinate> visited) {
        if (coord.row() < 0 || coord.row() >= maze.getHeight() ||
                coord.col() < 0 || coord.col() >= maze.getWidth()) {
            return;
        }

        Cell cell = maze.getCell(coord.row(), coord.col());
        if (cell.getType() != Cell.Type.PASSAGE || visited.contains(coord)) {
            return;
        }

        visited.add(coord);

        dfsValidate(maze, new Coordinate(coord.row() + 1, coord.col()), visited);
        dfsValidate(maze, new Coordinate(coord.row() - 1, coord.col()), visited);
        dfsValidate(maze, new Coordinate(coord.row(), coord.col() + 1), visited);
        dfsValidate(maze, new Coordinate(coord.row(), coord.col() - 1), visited);
    }
}

