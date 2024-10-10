package backend.academy.maze.service.generator.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.service.generator.Generator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DFSGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        generateMaze(maze, 0, 0);
        return maze;
    }

    private void generateMaze(Maze maze, int row, int col) {
        Cell current = maze.getCell(row, col);
        current.type(Cell.Type.PASSAGE);

        List<Coordinate> directions = getShuffledDirections();
        for (Coordinate dir : directions) {
            int newRow = row + dir.row() * 2;
            int newCol = col + dir.col() * 2;
            if (isValid(newRow, newCol, maze)) {
                maze.getCell(row + dir.row(), col + dir.col()).type(Cell.Type.PASSAGE);
                generateMaze(maze, newRow, newCol);
            }
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
        return row >= 0 && col >= 0 && row < maze.getHeight() && col < maze.getWidth() &&
                maze.getCell(row, col).type() == Cell.Type.WALL;
    }
}