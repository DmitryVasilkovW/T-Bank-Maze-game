package backend.academy.maze.model;

import backend.academy.maze.model.type.PassageType;
import lombok.Getter;

@Getter
public class Maze {

    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, PassageType.WALL);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }
}
