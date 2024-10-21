package backend.academy.maze.service.generator.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.type.SurfaceType;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.service.generator.Generator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DFSGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate end) {
        Maze maze = new Maze(height, width);
        generateMaze(maze, start.row(), start.col());

        Cell startCell = maze.getCell(start.row(), start.col());
        startCell.type(PassageType.PASSAGE);
        startCell.surface(SurfaceType.START);

        Cell endCell = maze.getCell(end.row(), end.col());
        endCell.type(PassageType.PASSAGE);
        endCell.surface(SurfaceType.END);

        return maze;
    }

    private void generateMaze(Maze maze, int row, int col) {
        Cell current = maze.getCell(row, col);
        current.type(PassageType.PASSAGE);

        current.surface(getRandomSurface());

        List<Coordinate> directions = getShuffledDirections();
        for (Coordinate dir : directions) {
            int newRow = row + dir.row() * 2;
            int newCol = col + dir.col() * 2;
            if (isValid(newRow, newCol, maze)) {
                maze.getCell(row + dir.row(), col + dir.col()).type(PassageType.PASSAGE);
                generateMaze(maze, newRow, newCol);
            }
        }
    }

    private SurfaceType getRandomSurface() {
        int randomValue = random.nextInt(10);
        if (randomValue < 2) {
            return SurfaceType.MUD;
        } else if (randomValue < 4) {
            return SurfaceType.SAND;
        } else if (randomValue == 9) {
            return SurfaceType.COIN;
        } else {
            return SurfaceType.NORMAL;
        }
    }

    private List<Coordinate> getShuffledDirections() {
        List<Coordinate> directions = List.of(
                new Coordinate(0, 1),
                new Coordinate(1, 0),
                new Coordinate(0, -1),
                new Coordinate(-1, 0)
        );
        List<Coordinate> shuffled = new ArrayList<>(directions);
        Collections.shuffle(shuffled);
        return shuffled;
    }

    private boolean isValid(int row, int col, Maze maze) {
        return row >= 0 && col >= 0 && row < maze.height() && col < maze.width() &&
                maze.getCell(row, col).type() == PassageType.WALL;
    }
}
