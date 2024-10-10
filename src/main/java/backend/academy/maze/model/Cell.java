package backend.academy.maze.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Cell {
    public enum Type { WALL, PASSAGE }

    private final int row;
    private final int col;
    @Setter
    private Type type;

    public Cell(int row, int col, Type type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

}